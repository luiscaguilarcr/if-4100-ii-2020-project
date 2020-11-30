package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Log;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.LogService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors.DatabaseService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors.UCRDatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LogProcessService implements LogService {
    private static LogProcessService instance;
    /* USER_EVENT_CODES [INFORMATION(0), CONFIRMATION(1), WARNING(2), DANGER(3), ERROR(4)] */
    public final int INFORMATION = 0;
    public final int CONFIRMATION = 1;
    public final int WARNING = 2;
    public final int DANGER = 3;
    public final int ERROR = 4;
    /* Database connector */
    private final DatabaseService databaseService;

    public static LogProcessService getInstance(){
        if (instance == null){
            instance = new LogProcessService(new UCRDatabaseService(false));
        }
        return instance;
    }

    private LogProcessService(final DatabaseService databaseService){
        this.databaseService = databaseService;
    }


    @Override
    public void sessionTokenGenerationLog(String CLIENT_TOKEN, String SESSION_TOKEN, int userId, String ipAddress) {
        try {
            final String insertStatement = "INSERT INTO [SessionToken] ( client_token, session_token, id_user, ip_address) values (?,?,?,?);";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertStatement);

            preparedStatement.setString(1, CLIENT_TOKEN); /* client_token */
            preparedStatement.setString(2, SESSION_TOKEN); /* session_token */
            preparedStatement.setInt(3, userId); /* id_user */
            preparedStatement.setString(4, ipAddress); /* ip_address */

            preparedStatement.execute();
        } catch (Exception e) {
            System.err.println("Error while attempt SessionTokenLog: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void sshTokenGenerationLog(String SESSION_TOKEN, String SSH_SESSION_TOKEN, int remoteServerId) {
        try {
            final String insertStatement = "INSERT INTO [SshToken] ([session_token], [ssh_token], [id_remote_server]) values (?,?,?);";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertStatement);

            preparedStatement.setString(1, SESSION_TOKEN); /* session_token */
            preparedStatement.setString(2, SSH_SESSION_TOKEN); /* ssh_token */
            preparedStatement.setInt(3, remoteServerId); /* id_remote_server */

            preparedStatement.execute();
        } catch (Exception e) {
            System.err.println("Error while attempt SessionTokenLog: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Sends log to database.
     *
     * @param SESSION_TOKEN session token.
     * @param type  type of the log (User(1), RemoteServer(2), SSHConnection(3).
     * @param category category of the log INFORMATION(1), CONFIRMATION(2), WARNING(3), DANGER(4), ERROR(5).
     * @param detail message with details of the log.
     */
    private void genericLog(String SESSION_TOKEN, int type, int category, String detail) {
        try {
            final String insertStatement = "INSERT INTO GenericLog ([session_token], [type], [category], [detail]) values (?,?,?,?);";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertStatement);

            preparedStatement.setString(1, SESSION_TOKEN); /* session_token */
            preparedStatement.setInt(2, type); /* type */
            preparedStatement.setInt(3, category); /* category */
            preparedStatement.setString(4, detail); /* detail */

            preparedStatement.execute();
        } catch (Exception e) {
            System.err.println("Error while attempt SessionTokenLog: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public void genericUserLog(String SESSION_TOKEN, int category, String detail) {
        genericLog(SESSION_TOKEN, 0, category, detail);
    }

    @Override
    public void genericRemoteServerLog(String SESSION_TOKEN, int category, String detail) {
        genericLog(SESSION_TOKEN, 1, category, detail);
    }

    @Override
    public void genericSSHConnectionLog(String SESSION_TOKEN, int category, String detail) {
        genericLog(SESSION_TOKEN, 2, category, detail);
    }
    @Override
    public List<Log> getLogs(){
        ResultSet resultSet;
        final List<Log> logs = new ArrayList<>();
        try {
            final String getStatement ="SELECT [type], [category], [session_token], [detail], [created_at] FROM [GenericLog];";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(getStatement);

            resultSet = preparedStatement.executeQuery();
            // Read all rows.
            while (resultSet.next()) {
                // Create a Logs
                logs.add(new Log(
                                resultSet.getString("created_at"),
                                resultSet.getInt("category"),
                                resultSet.getInt("type"),
                                resultSet.getString("session_token"),
                                resultSet.getString("detail")
                ));
            }
        } catch (Exception e) {

        }
        return logs;
    }
}
