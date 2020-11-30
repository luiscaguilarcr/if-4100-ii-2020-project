package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.transformation.UserPersistenceService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.UserService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.util.Utility;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.User;

/**
 * This services is use con validate, store and request for information related with users.
 * <p>
 * Implements the Singleton Pattern.
 */
public class UserBusinessService implements UserService<User> {
    private static UserBusinessService instance;
    private final UserService<User> userPersistenceService;
    /**
     * Constructor of the Class. Injects the persistence service.
     *
     * @param userPersistenceService UserService that manage the persistence of the ServerAdministrator's data.
     */
    private UserBusinessService(UserService<User> userPersistenceService) {
        this.userPersistenceService = userPersistenceService;
    }
    /**
     * Get the instance of the User Internal Service.
     *
     * @return {@code UserInternalService} instance of the service.
     */
    public static UserBusinessService getInstance() {
        if (instance == null) {
            instance = new UserBusinessService(UserPersistenceService.getInstance());
        }
        return instance;
    }
    /**
     * Search and returns an User by it's username.
     *
     * @param email email of the User.
     * @return {@code ServerAdministrator} if have been found. {@code null} if doesn't exist
     * an User with the username provided.
     * @throws BusinessException Exception threw if an ServiceException
     *                              occurs while is trying to get the User.
     */
    @Override
    public User get(String email) throws BusinessException, PersistenceException {
        if (email == null || email.equals("")) {
            throw new BusinessException("User's email not provided.", BusinessException.USER_EMAIL_NOT_PROVIDED);
        }
        if (!Utility.isEmailValid(email)) {
            throw new BusinessException("User's email not valid.", BusinessException.USER_EMAIL_NOT_VALID);
        }
        return userPersistenceService.get(email);
    }
    /**
     * Inserts a new User to the repository. This also validates if the
     * User is valid.
     *
     * @param user User to be added.
     * @return {@code true} if the User have been added, {@code false} otherwise.
     * @throws BusinessException Exception threw if an ServiceException
     *                              occurs while is trying to insert the
     *                              User.
     */
    @Override
    public boolean insert(User user) throws BusinessException, PersistenceException {
        validateUser(user);
        return userPersistenceService.insert(user);
    }
    /**
     * Deletes an User of the repository.
     *
     * @param user User to be deleted.
     * @return {@code true} if the User have been deleted, {@code false} otherwise.
     * @throws BusinessException Exception threw if an ServiceException
     *                              occurs while is trying to delete the
     *                              User.
     */
    @Override
    public boolean delete(User user) throws BusinessException, PersistenceException {
        validateUser(user);
        return userPersistenceService.delete(user);
    }
    /**
     * This change the email of the User.
     * Receives the User to be modified and the new email. This also check is the email is valid an unique.
     *
     * @param user     User to be modified.
     * @param newEmail User's new email.
     * @return {@code true} if the User have been modified, {@code false} otherwise.
     * @throws BusinessException Exception threw if an ServiceException
     *                              occurs while is trying to modify the
     *                              User.
     */
    @Override
    public boolean changeEmail(User user, String newEmail) throws BusinessException, PersistenceException {
        validateUser(user);
        if (user.getEmail() == null || user.getEmail().equals("")) {
            throw new BusinessException("User's new email not provided.", BusinessException.USER_NEW_EMAIL_NOT_PROVIDED);
        }
        if (!Utility.isEmailValid(user.getEmail())) {
            throw new BusinessException("User's new email not valid.", BusinessException.USER_NEW_EMAIL_NOT_VALID);
        }
        return Utility.isEmailValid(newEmail) && userPersistenceService.changeEmail(user, newEmail);

    }
    /**
     * This change the password of the User.
     * Receives the User to be modified and the new password. This also check is the old password
     * is correct.
     *
     * @param user        User to be modified.
     * @param newPassword User's new password.
     * @return {@code true} if the User have been modified, {@code false} otherwise.
     * @throws BusinessException Exception threw if an ServiceException
     *                              occurs while is trying to modify the
     *                              User.
     */
    @Override
    public boolean changePassword(final User user, final String newPassword) throws BusinessException, PersistenceException {
        validateUser(user);
        if (newPassword == null || newPassword.equals("")) {
            throw new BusinessException("User's new password not provided.", BusinessException.USER_NEW_PASSWORD_NOT_PROVIDED);
        }
        if (Utility.invalidPassword(newPassword)) {
            throw new BusinessException("User's new password is not secure enough.", BusinessException.USER_NEW_PASSWORD_NOT_SECURE);
        }
        return userPersistenceService.changePassword(user, newPassword);
    }
    /**
     * This change the name of the User.
     * Receives the User to be modified and the new firstname and lastname.
     *
     * @param user      User to be modified.
     * @param firstName User's new first name.
     * @param lastName  User's new last name.
     * @return {@code true} if the User have been modified, {@code false} otherwise.
     * @throws BusinessException Exception threw if an ServiceException
     *                              occurs while is trying to modify the
     *                              User.
     */
    @Override
    public boolean changeName(final User user, final String firstName, final String lastName) throws BusinessException, PersistenceException {
        validateUser(user);
        if (firstName == null || firstName.equals("")) {
            throw new BusinessException("User's new first name not provided.", BusinessException.USER_NEW_FIRST_NAME_NOT_PROVIDED);
        }
        if (lastName == null || lastName.equals("")) {
            throw new BusinessException("User's new last name not provided.", BusinessException.USER_NEW_LAST_NAME_NOT_PROVIDED);
        }
        return userPersistenceService.changeName(user, firstName, lastName);
    }
    /**
     * Validates basic required information about the user.
     *
     * @param user User to be validate.
     * @throws BusinessException Exception threw when the user information is not valid.
     */
    private void validateUser(final User user) throws BusinessException {
        if (user == null) {
            throw new BusinessException("User not provided.", BusinessException.USER_OBJECT_NOT_PROVIDED);
        }
        if (user.getEmail() == null || user.getEmail().equals("")) {
            throw new BusinessException("User's email not provided.", BusinessException.USER_EMAIL_NOT_PROVIDED);
        }
        if (!Utility.isEmailValid(user.getEmail())) {
            throw new BusinessException("User's email not valid.", BusinessException.USER_EMAIL_NOT_VALID);
        }
        if (user.getFirstName() == null || user.getFirstName().equals("")) {
            throw new BusinessException("User's first name not provided.", BusinessException.USER_FIRST_NAME_NOT_PROVIDED);
        }
        if (user.getLastName() == null || user.getLastName().equals("")) {
            throw new BusinessException("User's last name not provided.", BusinessException.USER_LAST_NAME_NOT_PROVIDED);
        }
        if (user.getPassword() == null || user.getPassword().equals("")) {
            throw new BusinessException("User's password not provided.", BusinessException.USER_PASSWORD_NOT_PROVIDED );
        }
        if (Utility.invalidPassword(user.getPassword())) {
            throw new BusinessException("User's password is not secure enough.", BusinessException.USER_PASSWORD_NOT_SECURE);
        }
    }
}
