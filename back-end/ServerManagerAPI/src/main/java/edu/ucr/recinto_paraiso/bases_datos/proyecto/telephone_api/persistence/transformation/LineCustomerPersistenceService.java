package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.transformation;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LineCustomer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.LineCustomerBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors.DatabaseService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors.UCRDatabaseService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.LineCustomerService;

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
public class LineCustomerPersistenceService implements LineCustomerService<LineCustomer> {
    /* Instance */
    private static LineCustomerPersistenceService instance;
    private final DatabaseService databaseService;

    /**
     * Constructor of the class. Receives an injection of the database connector that manages the data of the
     * ServerAdministrator.
     *
     * @param databaseService Database service.
     */
    private LineCustomerPersistenceService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    /**
     * Get the instance of the User Persistence Service.
     *
     * @return {@code UserPersistenceService} instance of the service.
     */
    public static LineCustomerPersistenceService getInstance() {
        if (instance == null) {
            instance = new LineCustomerPersistenceService(new UCRDatabaseService(false));//TODO Try generate connector
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
    public List<LineCustomer> get() throws PersistenceException {
        ResultSet resultSet;
        final List<LineCustomer> list = new ArrayList<>();
        try {
            /* Create statement */
            final String getStatement = "SELECT [Telephone_Number], [Customer_Id], [Customer_First_Name], [Customer_Last_Name], [Customer_Address], [Customer_Email] FROM [Line_Customer];";
            /* Establish connection */
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(getStatement);
            /* Execute statement */
            resultSet = preparedStatement.executeQuery();
            /* Read all rows (prevent null) */
            while (resultSet.next()) {
                /* Create a User */
                final LineCustomer lineCustomer = new LineCustomerBuilder()
                        .setTelephoneNumber(resultSet.getInt(LineCustomerColumnLabel.telephoneNumber))
                        .setId(resultSet.getInt(LineCustomerColumnLabel.id))
                        .setFirstName(resultSet.getString(LineCustomerColumnLabel.firstName))
                        .setLastName(resultSet.getString(LineCustomerColumnLabel.lastName))
                        .setAddress(resultSet.getString(LineCustomerColumnLabel.address))
                        .setEmail(resultSet.getString(LineCustomerColumnLabel.email))
                        .build();
                list.add(lineCustomer);
            }
            return list;
        } catch (SQLException e) {
            throw new PersistenceException("Can't get the user information from database. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }

    /**
     * Search and returns an User by it's username.
     *
     * @param telephoneNumber telephoneNumber
     * @return {@code User} if have been found. {@code null} if doesn't exist
     * an User with the username provided.
     */
    @Override
    public LineCustomer getByTelephoneNumber(int telephoneNumber) throws PersistenceException {
        ResultSet resultSet;
        try {
            /* Create statement */
            final String getStatement = "SELECT [Telephone_Number], [Customer_Id], [Customer_First_Name], [Customer_Last_Name], [Customer_Address], [Customer_Email] FROM [Line_Customer] WHERE [Telephone_Number] = ?;";
            /* Establish connection */
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(getStatement);
            /* Add parameters */
            preparedStatement.setInt(1, telephoneNumber);
            /* Execute statement */
            resultSet = preparedStatement.executeQuery();
            /* Read all rows (prevent null) */
            LineCustomer lineCustomer = null;
            while (resultSet.next()) {
                /* Create a User */
                 lineCustomer = new LineCustomerBuilder()
                        .setTelephoneNumber(resultSet.getInt(LineCustomerColumnLabel.telephoneNumber))
                        .setId(resultSet.getInt(LineCustomerColumnLabel.id))
                        .setFirstName(resultSet.getString(LineCustomerColumnLabel.firstName))
                        .setLastName(resultSet.getString(LineCustomerColumnLabel.lastName))
                        .setAddress(resultSet.getString(LineCustomerColumnLabel.address))
                        .setEmail(resultSet.getString(LineCustomerColumnLabel.email))
                        .build();
            }
            if(lineCustomer != null){
                return lineCustomer;
            }
            throw new PersistenceException("Customer not found" , 0);
        } catch (SQLException e) {
            throw new PersistenceException("Can't get the user information from database. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }

    /**
     * Inserts a new User to the repository. This also validates if the
     * User is valid.
     *
     * @param lineCustomer User to be added.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to insert the
     *                              User.
     */
    @Override
    public void insert(LineCustomer lineCustomer) throws BusinessException, PersistenceException {
        try {
            final String insertStatement = "INSERT INTO [Line_Customer] ([Telephone_Number], [Customer_Id], [Customer_First_Name], [Customer_Last_Name], [Customer_Address], [Customer_Email]) values (?,?,?,?,?,?);";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertStatement);
            /* Add parameters */
            preparedStatement.setInt(1, lineCustomer.getTelephoneNumber());
            preparedStatement.setInt(2, lineCustomer.getId());
            preparedStatement.setString(3, lineCustomer.getFirstName());
            preparedStatement.setString(4, lineCustomer.getLastName());
            preparedStatement.setString(5, lineCustomer.getAddress());
            preparedStatement.setString(6, lineCustomer.getEmail());
            /* Execute statement */
            preparedStatement.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                throw new BusinessException("Telephone number is already used.", BusinessException.LINE_CUSTOMER_TELEPHONE_NUMBER_IN_USE);
            } else {
                throw new PersistenceException("Error during insert execution. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
            }
        }
    }

    /**
     * Inserts a new User to the repository. This also validates if the
     * User is valid.
     *
     * @param lineCustomer Line.
     */
    @Override
    public void update(LineCustomer lineCustomer) throws PersistenceException {
        try {
            final String insertStatement = "UPDATE [Line_Customer] SET [Customer_First_Name] = ?, [Customer_Last_Name] = ?, [Customer_Address] = ?, [Customer_Email] = ? WHERE [Customer_Id] = ?";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertStatement);
            /* Add parameters */
            preparedStatement.setString(1, lineCustomer.getFirstName());
            preparedStatement.setString(2, lineCustomer.getLastName());
            preparedStatement.setString(3, lineCustomer.getAddress());
            preparedStatement.setString(4, lineCustomer.getEmail());
            preparedStatement.setInt(5, lineCustomer.getId());
            /* Execute statement */
            preparedStatement.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                throw new PersistenceException("Telephone number is already used.", BusinessException.LINE_CUSTOMER_TELEPHONE_NUMBER_IN_USE);
            } else {
                throw new PersistenceException("Error during insert execution. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
            }
        }
    }

    /**
     * Deletes an User of the repository.
     *
     * @param lineCustomer User to be deleted.
     * @return {@code true} if the User have been deleted, {@code false} otherwise.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to delete the
     *                              User.
     */
    @Override
    public boolean deleteByTelephoneNumber(LineCustomer lineCustomer) throws PersistenceException {
        try {
            final String deleteStatement = "DELETE FROM [Line_Customer] WHERE [Telephone_Number] = ?;";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(deleteStatement);
            /* Add parameters */
            preparedStatement.setInt(1, lineCustomer.getTelephoneNumber());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new PersistenceException("Error during delete execution. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }
}

/**
 * Names of the columns in database.
 */
class LineCustomerColumnLabel {
    /* Columns */
    static final String telephoneNumber = "Telephone_Number";
    static final String id = "Customer_Id";
    static final String firstName = "Customer_First_Name";
    static final String lastName = "Customer_Last_Name";
    static final String address = "Customer_Address";
    static final String email = "Customer_Email";
}
