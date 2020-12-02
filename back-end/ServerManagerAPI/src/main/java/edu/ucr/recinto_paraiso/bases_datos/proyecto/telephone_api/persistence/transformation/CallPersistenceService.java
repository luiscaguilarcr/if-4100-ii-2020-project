package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.transformation;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Call;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.CallBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors.DatabaseService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors.UCRDatabaseService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.CallService;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class with a default access. This class is a bridge between the Public access CallService and the database.
 * <p>
 * Implements the Singleton Pattern.
 */
public class CallPersistenceService implements CallService<Call> {
    /* Instance */
    private static CallPersistenceService instance;
    private final DatabaseService databaseService;
    /**
     * Constructor of the class. Receives an injection of the database connector that manages the data of the
     * ServerAdministrator.
     *
     * @param databaseService Database service.
     */
    private CallPersistenceService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
    /**
     * Get the instance of the Call Persistence Service.
     *
     * @return {@code UserPersistenceService} instance of the service.
     */
    public static CallPersistenceService getInstance() {
        if (instance == null) {
            instance = new CallPersistenceService(new UCRDatabaseService(false));//TODO Try generate connector
        }
        return instance;
    }
    /**
     * Search and returns an Call by it's telephone number.
     *
     * @return {@code Call} if have been found. {@code null} if doesn't exist
     * a Call with the telephone number provided.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to get the Call.
     */
    @Override
    public List<Call> get() throws PersistenceException {
        ResultSet resultSet;
        final List<Call> list = new ArrayList<>();
        try {
            /* Create statement */
            final String getStatement = "SELECT [Telephone_Number], [Destination_Telephone_Number], [Start_Date], [End_Date] FROM [Call];";
            /* Establish connection */
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(getStatement);
            /* Execute statement */
            resultSet = preparedStatement.executeQuery();
            /* Read all rows (prevent null) */
            while (resultSet.next()) {
                /* Create a User */
                final Call newCall = new CallBuilder()
                        .setTelephone_Number(resultSet.getInt(CallColumnLabel.telephone_Number))
                        .setDestination_Telephone_Number(resultSet.getInt(CallColumnLabel.destination_Telephone_Number))
                        .setStart_Date(resultSet.getString(CallColumnLabel.start_Date))
                        .setEnd_Date(resultSet.getString(CallColumnLabel.end_Date))
                        .build();
                list.add(newCall);
            }
            return list;
        } catch (SQLException e) {
            throw new PersistenceException("Can't get the call information from database. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }

    /**
     * Inserts a new Call to the repository. This also validates if the
     * Call is valid.
     *
     * @param call Call to be added.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to insert the
     *                              Call.
     */
    @Override
    public boolean insert(Call call) throws PersistenceException {
        try {
            final String insertStatement = "INSERT INTO [call] (Telephone_Number, destination_Telephone_Number, start_Date, end_Date) values (?,?,?,?);";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertStatement);
            /* Add parameters */
            preparedStatement.setInt(1, call.getTelephone_Number());
            preparedStatement.setInt(2, call.getDestinationTelephoneNumber());
            preparedStatement.setDate(3, Date.valueOf(call.getStartDate())); // TODO verify transformation. Exception generated here!
            preparedStatement.setDate(4, Date.valueOf(call.getEndDate())); // TODO verify transformation. Exception generated here!
            /* Execute statement */
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new PersistenceException("Error during insert execution. Details: " + e.getMessage(), PersistenceException.DATABASE_EXECUTION_FAILED);
        }
        return true;
    }

    /**
     * Update a Call into the repository. This also validates if the
     * Call is valid.
     *
     * @param call Call to be updated.
     */
    @Override
    public boolean update(Call call) throws PersistenceException {
        try {
            final String insertStatement = "UPDATE [Call] SET [Destination_Telephone_Number] = ?, [Start_Date] = ?, [End_Date] = ? WHERE [No_Call] = ?"; // TODO Change to id
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertStatement);
            /* Add parameters */
            preparedStatement.setInt(1, call.getDestinationTelephoneNumber());
            preparedStatement.setString(2, call.getStartDate());// TODO verify transformation. Exception generated here!
            preparedStatement.setString(3, call.getEndDate());// TODO verify transformation. Exception generated here!
            preparedStatement.setInt(4, call.getTelephone_Number());// TODO verify transformation. Exception generated here!
            /* Execute statement */
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new PersistenceException("Error during update execution. Details: " + e.getMessage(), PersistenceException.DATABASE_EXECUTION_FAILED);
        }
        return true;
    }

    /**
     * Deletes a Call of the repository.
     *
     * @param call Call to be deleted.
     * @return {@code true} if the User have been deleted, {@code false} otherwise.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to delete the
     *                              Call.
     */
    @Override
    public boolean delete(Call call) throws PersistenceException {
        try {
            final String deleteStatement = "DELETE FROM [Call] WHERE [Telephone_Number] = ?;";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(deleteStatement);
            /* Add parameters */
            preparedStatement.setInt(1, call.getTelephone_Number());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new PersistenceException("Error during delete execution. Details: " + e.getMessage(), PersistenceException.DATABASE_EXECUTION_FAILED);
        }
    }

}

/**
 * Names of the columns in database.
 */
class CallColumnLabel {
    /* Columns */
    static final String telephone_Number = "Telephone_Number";
    static final String destination_Telephone_Number = "Destination_Telephone_Number";
    static final String start_Date = "Start_Date";
    static final String end_Date = "End_Date";
}
