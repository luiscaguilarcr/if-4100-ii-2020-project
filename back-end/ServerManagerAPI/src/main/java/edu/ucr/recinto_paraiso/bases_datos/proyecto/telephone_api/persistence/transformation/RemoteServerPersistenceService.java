package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.transformation;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.RemoteServer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.RemoteServerBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.SSHConfigurationBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors.DatabaseService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.RemoteServerService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors.UCRDatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Class with a default access. This class is a bridge between the Public access RemoteServer Service and the database.
 * <p>
 * Implements the Singleton Pattern.
 */
public class RemoteServerPersistenceService implements RemoteServerService<RemoteServer> {
    /* Instance */
    private static RemoteServerPersistenceService instance;
    private final DatabaseService databaseService;
    /**
     * Constructor of the class. Receives an injection of the remote server persistence service
     * that manages the data of the RemoteServer.
     *
     * @param databaseService Remote Server database service.
     */
    private RemoteServerPersistenceService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
    /**
     * Get the instance of the RemoteServer Persistence Service.
     *
     * @return {@code UserPersistenceService} instance of the service.
     */
    public static RemoteServerPersistenceService getInstance() {
        if (instance == null) {
            instance = new RemoteServerPersistenceService(new UCRDatabaseService(false));//TODO Try generate new connector
        }
        return instance;
    }
    /**
     * Search and returns the list of remote servers linked with the user's id provided.
     *
     * @param id ID of the remote server Administrator.
     * @return {@code List<E>} List of remote servers found.
     * @throws PersistenceException Exception threw when an RemoteServer-
     *                              ServiceException occurs while is trying
     *                              to get the list of servers.
     */
    @Override
    public List<RemoteServer> getByUserId(Integer id) throws PersistenceException {
        final List<RemoteServer> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            /* Statement */
            final String getStatement = "SELECT * FROM [RemoteServer] WHERE [id_user] = ?;";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(getStatement);
            /* Add parameters */
            preparedStatement.setInt(1, id); /* user id */
            /* Execute statement */
            resultSet = preparedStatement.executeQuery();
            /* Process statement response */
            while (resultSet.next()) {
                /* Create RemoteServer Object  */
                final RemoteServer remoteServer = new RemoteServerBuilder()
                        .setId(resultSet.getInt(RemoteServerColumnLabel.idRemoteServer))
                        .setAdminId(resultSet.getInt(RemoteServerColumnLabel.idUser))
                        .setLabel(resultSet.getString(RemoteServerColumnLabel.label))
                        .setDescription(resultSet.getString(RemoteServerColumnLabel.description))
                        .setOperativeSystem(resultSet.getString(RemoteServerColumnLabel.operativeSystem))
                        .setVersion(resultSet.getString(RemoteServerColumnLabel.version))
                        .setSshConfiguration(new SSHConfigurationBuilder()
                                .setServerAddress(resultSet.getString(RemoteServerColumnLabel.ipAddress))
                                .setUsername(resultSet.getString(RemoteServerColumnLabel.username))
                                .setPassword(resultSet.getString(RemoteServerColumnLabel.password))
                                .setPort(resultSet.getInt(RemoteServerColumnLabel.port))
                                .setSessionTime(resultSet.getInt(RemoteServerColumnLabel.sessionTime))
                                .build()
                        ).build();
                list.add(remoteServer);
            }
            return list;
        } catch (SQLException e) {
            throw new PersistenceException("Error during get execution. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }
    /**
     * Search and returns the remote servers linked with the user's id and server id provided.
     *
     * @param userId   ID of the user.
     * @param serverId ID of remote server.
     * @return {@code E} Remote server found.
     * @throws PersistenceException Exception threw when an RemoteServer-
     *                              ServiceException occurs while is trying
     *                              to get the list of servers.
     */
    @Override
    public RemoteServer getServer(final Integer userId, final Integer serverId) throws PersistenceException {
        ResultSet resultSet;
        try {
            /* Create statement */
            final String getStatement = "SELECT * FROM [RemoteServer] WHERE [id_user] = ? AND [id_remote_server] = ?;";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(getStatement);
            /* Add parameters */
            preparedStatement.setInt(1, userId); /* user id */
            preparedStatement.setInt(2, serverId); /* remote server id */
            /* Execute statement */
            resultSet = preparedStatement.executeQuery();
            /* Process statement response */
            RemoteServer remoteServer = null;
            while (resultSet.next()) {
                // Create a RemoteServer
                remoteServer = new RemoteServerBuilder()
                        .setId(resultSet.getInt(RemoteServerColumnLabel.idRemoteServer))
                        .setAdminId(resultSet.getInt(RemoteServerColumnLabel.idUser))
                        .setLabel(resultSet.getString(RemoteServerColumnLabel.label))
                        .setDescription(resultSet.getString(RemoteServerColumnLabel.description))
                        .setOperativeSystem(resultSet.getString(RemoteServerColumnLabel.operativeSystem))
                        .setVersion(resultSet.getString(RemoteServerColumnLabel.version))
                        .setSshConfiguration(new SSHConfigurationBuilder()
                                .setServerAddress(resultSet.getString(RemoteServerColumnLabel.ipAddress))
                                .setUsername(resultSet.getString(RemoteServerColumnLabel.username))
                                .setPassword(resultSet.getString(RemoteServerColumnLabel.password))
                                .setPort(resultSet.getInt(RemoteServerColumnLabel.port))
                                .setSessionTime(resultSet.getInt(RemoteServerColumnLabel.sessionTime))
                                .build()
                        ).build();
            }
            return remoteServer;
        } catch (SQLException e) {
            throw new PersistenceException("Error during get execution. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }
    /**
     * Inserts a new RemoteServer to the repository. This also validates if the
     * server is valid.
     *
     * @param remoteServer RemoteServer to be added.
     * @return {@code true} if the RemoteServer have been added, {@code false} otherwise.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to insert the
     *                              remote server.
     */
    @Override
    public boolean insert(RemoteServer remoteServer) throws PersistenceException {
        try {
            /* Create statement */
            final String insertStatement = "INSERT INTO [RemoteServer] " +
                    "(id_user, label, description, operative_system, version," +
                    "ip_address, username, password, port, session_time) VALUES (?,?,?,?,?,?,?,?,?,?)";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertStatement);
            /* Add parameters */
            preparedStatement.setInt(1, remoteServer.getAdminId()); /* user id */
            preparedStatement.setString(2, remoteServer.getLabel()); /* label */
            preparedStatement.setString(3, remoteServer.getDescription()); /* description */
            preparedStatement.setString(4, remoteServer.getOperativeSystem()); /* operative System */
            preparedStatement.setString(5, remoteServer.getVersion()); /* version */
            preparedStatement.setString(6, remoteServer.getSshConfiguration().getServerAddress()); /* ip address */
            preparedStatement.setString(7, remoteServer.getSshConfiguration().getUsername()); /* username */
            preparedStatement.setString(8, remoteServer.getSshConfiguration().getPassword()); /* password */
            preparedStatement.setInt(9, remoteServer.getSshConfiguration().getPort()); /* port */
            preparedStatement.setInt(10, remoteServer.getSshConfiguration().getSessionTime()); /* session time */
            /* Execute statement */
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            throw new PersistenceException("Error during insert execution. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }
    /**
     * This updates RemoteServer.
     * Receives the RemoteServer to be modified. This also checks if the update is valid.
     *
     * @param remoteServer RemoteServer to be modified.
     * @return {@code true} if the User have been modified, {@code false} otherwise.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to modify the
     *                              remote server.
     */
    @Override
    public boolean update(RemoteServer remoteServer) throws PersistenceException {
        try {
            /* Create statement */
            final String updateStatement = "UPDATE [RemoteServer] SET " +
                    "[label] = ?, [description] = ?, [operative_system] = ?, [version] = ?, " +
                    "[ip_address] = ?, [username] = ?, [password] = ?, [port] = ?, [session_time] = ? " +
                    "WHERE [id_remote_server] = ?;";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(updateStatement);
            /* Add parameters */
            preparedStatement.setString(1, remoteServer.getLabel()); /* label */
            preparedStatement.setString(2, remoteServer.getDescription()); /* description */
            preparedStatement.setString(3, remoteServer.getOperativeSystem()); /* operative System */
            preparedStatement.setString(4, remoteServer.getVersion()); /* version */
            preparedStatement.setString(5, remoteServer.getSshConfiguration().getServerAddress()); /* ip address */
            preparedStatement.setString(6, remoteServer.getSshConfiguration().getUsername()); /* username */
            preparedStatement.setString(7, remoteServer.getSshConfiguration().getPassword()); /* password */
            preparedStatement.setInt(8, remoteServer.getSshConfiguration().getPort()); /* port */
            preparedStatement.setInt(9, remoteServer.getSshConfiguration().getSessionTime()); /* session time */
            preparedStatement.setInt(10, remoteServer.getId()); /* server id */
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new PersistenceException("Error during update execution. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }
    /**
     * Deletes a RemoteServer of the repository.
     *
     * @param remoteServer RemoteServer to be deleted.
     * @return {@code true} if the RemoteServer have been added, {@code false} otherwise.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to delete the
     *                              remote server.
     */
    @Override
    public boolean delete(RemoteServer remoteServer) throws PersistenceException {
        try {
            /* Create statement */
            final String deleteStatement = "DELETE FROM [RemoteServer] WHERE [id_remote_server] = ?;";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(deleteStatement);
            /* Add parameters */
            preparedStatement.setInt(1, remoteServer.getId()); /* remote server id */
            /* Execute statements */
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new PersistenceException("Error during delete execution. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }
}

/**
 * Names of the columns in database.
 */
class RemoteServerColumnLabel {
    /* Columns */
    static final String idRemoteServer = "id_remote_server";
    static final String idUser = "id_user";
    static final String label = "label";
    static final String description = "description";
    static final String operativeSystem = "operative_system";
    static final String version = "version";
    static final String ipAddress = "ip_address";
    static final String username = "username";
    static final String password = "password";
    static final String port = "port";
    static final String sessionTime = "session_time";
}