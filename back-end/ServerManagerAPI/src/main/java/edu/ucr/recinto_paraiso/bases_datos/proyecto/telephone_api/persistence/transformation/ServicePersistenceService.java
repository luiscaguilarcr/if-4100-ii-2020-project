package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.transformation;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Line;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.LineBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors.DatabaseService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors.UCRDatabaseService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.LineService;

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
public class ServicePersistenceService implements ServiceService<Service> {
    /* Instance */
    private static ServicePersistenceService instance;
    private final DatabaseService databaseService;
    /**
     * Constructor of the class. Receives an injection of the database connector that manages the data of the
     * ServerAdministrator.
     *
     * @param databaseService Database service.
     */
    private ServicePersistenceService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
    /**
     * Get the instance of the User Persistence Service.
     *
     * @return {@code UserPersistenceService} instance of the service.
     */
    public static ServicePersistenceService getInstance() {
        if (instance == null) {
            instance = new ServicePersistenceService(new UCRDatabaseService(false));//TODO Try generate connector
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
    public Service<Service> get() throws PersistenceException {
        ResultSet resultSet;
        final List<Service> list = new ArrayList<>();
        try {
            /* Create statement */
            final String getStatement = "SELECT [Service_Code], [Name], [Description], [Cost], [Status] FROM [Service];";
            /* Establish connection */
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(getStatement);
            /* Execute statement */
            resultSet = preparedStatement.executeQuery();
            /* Read all rows (prevent null) */
            while (resultSet.next()) {
                /* Create a User */
                final Service service = new ServiceBuilder()
                        .setService_Code(resultSet.getInt(ServiceColumnLabel.Service_Code))
                        .setName(resultSet.getInt(ServiceColumnLabel.Name))
                        .setDescription(resultSet.getInt(ServiceColumnLabel.Description))
                        .setCost(resultSet.getInt(ServiceColumnLabel.Cost))
                        .setStatus(resultSet.getString(ServiceColumnLabel.status))
                        .build();
                list.add(service);
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
    public void insert(Service service) throws PersistenceException {
        try {
            final String insertStatement = "INSERT INTO [Service] (Service_Code, Name, Description,Cost, Status) values (?,?,?,?,?);";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertStatement);
            /* Add parameters */
            preparedStatement.setInt(1, service.getService_Code());
            preparedStatement.setString(2, service.getName());
            preparedStatement.setString(3, service.getDescription());
            preparedStatement.setInt(4, service.getCost());
            preparedStatement.setString(5, String.valueOf(service.getStatus()));
            /* Execute statement */
            preparedStatement.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                throw new PersistenceException("Telephone number is already used.", BusinessException.USER_EMAIL_IN_USE);
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
    public void update(Service service) throws PersistenceException {
        try {
            final String insertStatement = "UPDATE [Service] SET [Name] = ?, [Description] = ?,[Cost] = ?,  [Status] = ? WHERE [Service_Code] = ?";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertStatement);
            /* Add parameters */
           preparedStatement.setString(2, service.getName());
            preparedStatement.setString(3, service.getDescription());
            preparedStatement.setInt(4, service.getCost());
            preparedStatement.setString(5, String.valueOf(service.getStatus()));
            /* Execute statement */
            preparedStatement.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                throw new PersistenceException("Telephone number is already used.", BusinessException.USER_EMAIL_IN_USE);
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
    public boolean delete(Service service) throws PersistenceException {
        try {
            final String deleteStatement = "DELETE FROM [Service] WHERE [Service_Code] = ?;";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(deleteStatement);
            /* Add parameters */
            preparedStatement.setInt(1, service.getService_Code());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new PersistenceException("Error during delete execution. Details: " + e.getMessage(), 0);
        }
    }
}

/**
 * Names of the columns in database.
 */
class ServiceColumnLabel {
    /* Columns */
    static final Int service_Code = "Service_Code";
    static final String name = "Name";
    static final String description = "Description";
    static final Int cost = "Cost";
    static final String status = "Status";


}
