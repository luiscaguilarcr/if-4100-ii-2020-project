package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.transformation;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.User;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.UserBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors.DatabaseService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.UserService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors.UCRDatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class with a default access. This class is a bridge between the Public access UserService and the database.
 * <p>
 * Implements the Singleton Pattern.
 */
public class UserPersistenceService implements UserService<User> {
    /* Instance */
    private static UserPersistenceService instance;
    private final DatabaseService databaseService;
    /**
     * Constructor of the class. Receives an injection of the database connector that manages the data of the
     * ServerAdministrator.
     *
     * @param databaseService Database service.
     */
    private UserPersistenceService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
    /**
     * Get the instance of the User Persistence Service.
     *
     * @return {@code UserPersistenceService} instance of the service.
     */
    public static UserPersistenceService getInstance() {
        if (instance == null) {
            instance = new UserPersistenceService(new UCRDatabaseService(false));//TODO Try generate connector
        }
        return instance;
    }
    /**
     * Search and returns an User by it's username.
     *
     * @param email Email of the User.
     * @return {@code User} if have been found. {@code null} if doesn't exist
     * an User with the email provided.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to get the User.
     */
    @Override
    public User get(String email) throws PersistenceException {
        ResultSet resultSet;
        User user = null;
        try {
            /* Create statement */
            final String getStatement =
                    "SELECT [id_user], [email], [password], [first_name], [last_name] " +
                            "FROM [User] " +
                            "WHERE [email] = ?;";
            /* Establish connection */
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(getStatement);
            /* Add parameters */
            preparedStatement.setString(1, email); /* email */
            /* Execute statement */
            resultSet = preparedStatement.executeQuery();
            /* Read all rows (prevent null) */
            while (resultSet.next()) {
                /* Create a User */
                user = new UserBuilder()
                        .setId(resultSet.getInt(UserColumnLabel.idUser))
                        .setEmail(resultSet.getString(UserColumnLabel.email))
                        .setPassword(resultSet.getString(UserColumnLabel.password))
                        .setFirstName(resultSet.getString(UserColumnLabel.firstName))
                        .setLastName(resultSet.getString(UserColumnLabel.lastName))
                        .build();
            }
            return user;
        } catch (SQLException e) {
            throw new PersistenceException("Can't get the user information from database. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }
    /**
     * Inserts a new User to the repository. This also validates if the
     * User is valid.
     *
     * @param user User to be added.
     * @return {@code true} if the User have been added, {@code false} otherwise.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to insert the
     *                              User.
     */
    @Override
    public boolean insert(User user) throws PersistenceException {
        try {
            final String insertStatement = "INSERT INTO [User] (email, password, first_name, last_name) values (?,?,?,?);";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(insertStatement);
            /* Add parameters */
            preparedStatement.setString(1, user.getEmail()); /* email */
            preparedStatement.setString(2, user.getPassword()); /* password */
            preparedStatement.setString(3, user.getFirstName()); /* firstName */
            preparedStatement.setString(4, user.getLastName()); /* lastName */
            /* Execute statement */
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                throw new PersistenceException("User email is not available.", BusinessException.USER_EMAIL_IN_USE);
            } else {
                throw new PersistenceException("Error during insert execution. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
            }
        }
    }
    /**
     * Deletes an User of the repository.
     *
     * @param user User to be deleted.
     * @return {@code true} if the User have been deleted, {@code false} otherwise.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to delete the
     *                              User.
     */
    @Override
    public boolean delete(User user) throws PersistenceException {
        try {
            final String deleteStatement = "DELETE FROM [User] WHERE [email] = ?;";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(deleteStatement);

            preparedStatement.setString(1, user.getEmail()); /* email */
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new PersistenceException("Error during delete execution. Details: " + e.getMessage(), 0);
        } catch (PersistenceException e) {
            throw new PersistenceException("Error connection with database. Details: " + e.getMessage(), 0);
        }
    }
    /**
     * This change the email of the User.
     * Receives the User to be modified and the new email. This also check is the email is valid an unique.
     *
     * @param user     User to be modified.
     * @param newEmail User's new email.
     * @return {@code true} if the User have been modified, {@code false} otherwise.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to modify the
     *                              User.
     */
    @Override
    public boolean changeEmail(User user, String newEmail) throws PersistenceException {
        try {
            final String updateStatement = "UPDATE [User] SET [email] = ? WHERE [email] = ? AND [password] = ?;";
            databaseService.connect();
            /* Prepare statement */
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(updateStatement);
            /* Add parameters */
            preparedStatement.setString(1, newEmail); /* New email */
            preparedStatement.setString(2, user.getEmail()); /* Current email */
            preparedStatement.setString(3, user.getPassword()); /* Current password */
            /* Execute statement */
            return preparedStatement.executeUpdate() == 1; // TODO CHECK OF UPDATE IN CASCADE AFFECTS THE N ROWS.
        } catch (SQLException e) {
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                throw new PersistenceException("User's new email is not available.", BusinessException.USER_EMAIL_IN_USE);
            } else {
                throw new PersistenceException("Error during email update execution. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
            }
        }
    }
    /**
     * This change the password of the User.
     * Receives the User to be modified and the new password. This also check is the old password
     * is correct.
     *
     * @param user        User to be modified.
     * @param newPassword User's new password.
     * @return {@code true} if the User have been modified, {@code false} otherwise.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to modify the
     *                              User.
     */
    @Override
    public boolean changePassword(User user, String newPassword) throws PersistenceException {
        try {
            final String updateStatement = "UPDATE [User] SET [password] = ? WHERE [email] = ? AND [password] = ?;";
            databaseService.connect();
            /* Prepare statement */
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(updateStatement);
            /* Add parameters */
            preparedStatement.setString(1, newPassword); /* New password */
            preparedStatement.setString(2, user.getEmail()); /* Current email */
            preparedStatement.setString(3, user.getPassword()); /* Current password */
            /* Execute statement */
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new PersistenceException("Error during password update execution. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }
    /**
     * This change the name of the User.
     * Receives the User to be modified and the new firstname and lastname.
     *
     * @param user      User to be modified.
     * @param firstName User's new first name.
     * @param lastName  User's new last name.
     * @return {@code true} if the User have been modified, {@code false} otherwise.
     * @throws PersistenceException Exception threw if an ServiceException
     *                              occurs while is trying to modify the
     *                              User.
     */
    @Override
    public boolean changeName(User user, String firstName, String lastName) throws PersistenceException {
        try {
            final String updateStatement = "UPDATE [User] SET [first_name] = ?, [last_name] = ? WHERE [email] = ? AND [password] = ?;";
            databaseService.connect();
            /* Prepare statement */
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(updateStatement);
            /* Add parameters */
            preparedStatement.setString(1, firstName);          /* New firstName */
            preparedStatement.setString(2, lastName);           /* New lastName */
            preparedStatement.setString(3, user.getEmail());    /* Current email */
            preparedStatement.setString(4, user.getPassword()); /* Current password */
            /* Execute statement */
            return preparedStatement.executeUpdate() == 1; // TODO CHECK OF UPDATE IN CASCADE AFFECTS THE N ROWS. IF IS THE CASE USE <=
        } catch (SQLException e) {
            throw new PersistenceException("Error during password update execution. Details: " + e.getMessage(),
                    PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }
}
/**
 * Names of the columns in database.
 */
class UserColumnLabel {
    /* Columns */
    static final String idUser = "id_user";
    static final String email = "email";
    static final String password = "password";
    static final String firstName = "first_name";
    static final String lastName = "last_name";
}
