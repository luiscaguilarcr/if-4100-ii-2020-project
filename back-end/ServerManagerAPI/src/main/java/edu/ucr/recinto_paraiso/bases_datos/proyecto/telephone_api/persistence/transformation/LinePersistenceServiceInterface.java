package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.transformation;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Line;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LineCallServiceCustomer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.LineBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.LineCallServiceCustomerBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors.DatabaseService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors.UCRDatabaseService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.LineCallServiceCustomerInterface;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.LineServiceInterface;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Class with a default access. This class is a bridge between the Public access UserService and the database.
 * <p>
 * Implements the Singleton Pattern.
 */
public class LinePersistenceServiceInterface implements LineServiceInterface<Line> {
    /* Instance */
    private static LinePersistenceServiceInterface instance;
    private final DatabaseService databaseService;
    /**
     * Constructor of the class. Receives an injection of the database connector that manages the data of the
     * ServerAdministrator.
     *
     * @param databaseService Database service.
     */
    private LinePersistenceServiceInterface(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
    /**
     * Get the instance of the User Persistence Service.
     *
     * @return {@code UserPersistenceService} instance of the service.
     */
    public static LinePersistenceServiceInterface getInstance() {
        if (instance == null) {
            instance = new LinePersistenceServiceInterface(new UCRDatabaseService(false));//TODO Try generate connector
        }
        return instance;
    }
    /**
     * Search and returns an User by it's username.
     *
     * @return {@code User} if have been found. {@code null} if doesn't exist
     * an User with the email provided.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to get the User.
     */
    @Override
    public List<Line> get() throws PersistenceException {
        ResultSet resultSet;
        final List<Line> list = new ArrayList<>();
        try {
            /* Create statement */
            final String getStatement = "SELECT [Telephone_Number], [Points_Quantity], [Type], [Status] FROM [Line];";
            /* Establish connection */
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(getStatement);
            /* Execute statement */
            resultSet = preparedStatement.executeQuery();
            /* Read all rows (prevent null) */
            while (resultSet.next()) {
                /* Create a User */
                final Line line = new LineBuilder()
                        .setTelephone_Number(resultSet.getInt(LineColumnLabel.telephone_Number))
                        .setPointsQuantity(resultSet.getInt(LineColumnLabel.points_Quantity))
                        .setType(resultSet.getInt(LineColumnLabel.type))
                        .setStatus(resultSet.getString(LineColumnLabel.status))
                        .build();
                list.add(line);
            }
            return list;
        } catch (SQLException e) {
            throw new PersistenceException("Can't get the user information from database. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }
    /**
     * Inserts a new User to the repository. This also validates if the
     * User is valid.
     *
     * @param line User to be added.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to insert the
     *                              User.
     */
    @Override
    public void insert(Line line) throws BusinessException, PersistenceException {
        try {
            final String insertStatement = "INSERT INTO [Line] (Telephone_Number, Points_Quantity, Type, Status) values (?,?,?,?);";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertStatement);
            /* Add parameters */
            preparedStatement.setInt(1, line.getTelephoneNumber());
            preparedStatement.setInt(2, line.getPointsQuantity());
            preparedStatement.setInt(3, line.getType());
            preparedStatement.setString(4, String.valueOf(line.getStatus()));
            /* Execute statement */
            preparedStatement.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                throw new BusinessException("Telephone number is already used.", BusinessException.LINE_TELEPHONE_NUMBER_IN_USE);
            } else {
                throw new PersistenceException("Error during insert execution. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
            }
        }
    }

    /**
     * Inserts a new User to the repository. This also validates if the
     * User is valid.
     *
     * @param line Line.
     */
    @Override
    public void update(Line line) throws PersistenceException {
        try {
            final String insertStatement = "UPDATE [Line] SET [Points_Quantity] = ?, [Type] = ?, [Status] = ? WHERE [Telephone_Number] = ?";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertStatement);
            /* Add parameters */
            preparedStatement.setInt(1, line.getPointsQuantity());
            preparedStatement.setInt(2, line.getType());
            preparedStatement.setString(3, line.getStatus());
            preparedStatement.setInt(4, line.getTelephoneNumber());
            /* Execute statement */
            preparedStatement.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                throw new PersistenceException("Telephone number is already used.", BusinessException.LINE_TELEPHONE_NUMBER_IN_USE);
            } else {
                throw new PersistenceException("Error during insert execution. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
            }
        }
    }

    /**
     * Deletes an User of the repository.
     *
     * @param line User to be deleted.
     * @return {@code true} if the User have been deleted, {@code false} otherwise.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to delete the
     *                              User.
     */
    @Override
    public boolean delete(Line line) throws PersistenceException {
        try {
            final String deleteStatement = "DELETE FROM [Line] WHERE [Telephone_Number] = ?;";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(deleteStatement);
            /* Add parameters */
            preparedStatement.setInt(1, line.getTelephoneNumber());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new PersistenceException("Error during delete execution. Details: " + e.getMessage(), 0);
        }
    }

    @Override
    public List<LineCallServiceCustomer> getAll() throws BusinessException, PersistenceException {
        ResultSet resultSet;
        final List<LineCallServiceCustomer> list = new ArrayList<>();
        try {
            /* Create statement */
            final String getStatement = "SELECT [Telephone_Number], [Customer_Id], [Customer_First_Name], [Customer_Last_Name], [Status], [Name] FROM [Line_Call_Service_Customer];";
            /* Establish connection */
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(getStatement);
            /* Execute statement */
            resultSet = preparedStatement.executeQuery();
            /* Read all rows (prevent null) */
            while (resultSet.next()) {
                /* Create a User */
                final LineCallServiceCustomer line = new LineCallServiceCustomerBuilder()
                        .setTelephoneNumber(resultSet.getInt(LineCallServiceCustomerColumnLabel.telephone_Number))
                        .setCustomerId(resultSet.getString(LineCallServiceCustomerColumnLabel.customer_Id))
                        .setCustomerFirstName(resultSet.getString(LineCallServiceCustomerColumnLabel.customer_First_Name))
                        .setCustomerLastName(resultSet.getString(LineCallServiceCustomerColumnLabel.customer_Last_Name))
                        .setStatus(resultSet.getString(LineCallServiceCustomerColumnLabel.status))
                        .setName(resultSet.getString(LineCallServiceCustomerColumnLabel.name))
                        .build();
                list.add(line);
            }
            return list;
        } catch (SQLException e) {
            throw new PersistenceException("Can't get the user information from database. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }

    @Override
    public List<LineCallServiceCustomer> get(String serviceName, String status) throws BusinessException, PersistenceException {
        ResultSet resultSet;
        final List<LineCallServiceCustomer> list = new ArrayList<>();
        try {
            /* Create statement */
            final String getStatement = "SELECT [Telephone_Number], [Customer_Id], [Customer_First_Name], [Customer_Last_Name], [Status], [Name] FROM [Service_Line_Call] WHERE [Name] = ? AND [Status] = ?;";
            /* Establish connection */
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(getStatement);
            preparedStatement.setString(1, serviceName);
            preparedStatement.setString(2, status);
            /* Execute statement */
            resultSet = preparedStatement.executeQuery();
            /* Read all rows (prevent null) */
            while (resultSet.next()) {
                /* Create a User */
                final LineCallServiceCustomer line = new LineCallServiceCustomerBuilder()
                        .setTelephoneNumber(resultSet.getInt(LineCallServiceCustomerColumnLabel.telephone_Number))
                        .setCustomerId(resultSet.getString(LineCallServiceCustomerColumnLabel.customer_Id))
                        .setCustomerFirstName(resultSet.getString(LineCallServiceCustomerColumnLabel.customer_First_Name))
                        .setCustomerLastName(resultSet.getString(LineCallServiceCustomerColumnLabel.customer_Last_Name))
                        .setStatus(resultSet.getString(LineCallServiceCustomerColumnLabel.status))
                        .setName(resultSet.getString(LineCallServiceCustomerColumnLabel.name))
                        .build();
                list.add(line);
            }
            return list;
        } catch (SQLException e) {
            throw new PersistenceException("Can't get the user information from database. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }
}

/**
 * Names of the columns in database.
 */
class LineCallServiceCustomerColumnLabel {
    /* Columns */
    static final String telephone_Number = "Telephone_Number";
    static final String customer_Id = "Customer_Id";
    static final String customer_First_Name = "Customer_First_Name";
    static final String customer_Last_Name = "Customer_Last_Name";
    static final String status = "Status";
    static final String name = "Name";
}

/**
 * Names of the columns in database.
 */
class LineColumnLabel {
    /* Columns */
    static final String telephone_Number = "Telephone_Number";
    static final String points_Quantity = "Points_Quantity";
    static final String type = "Type";
    static final String status = "Status";
}
