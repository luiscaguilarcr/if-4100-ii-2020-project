package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;

/**
 * This is an user interface, that defines the schema required to work with user objects.
 */
public interface UserService<E> {
    /**
     * Search and returns an User by it's username.
     *
     * @param email Email of the User.
     * @return {@code User} if have been found. {@code null} if doesn't exist
     * an User with the username provided.
     */
    E get(String email) throws BusinessException, PersistenceException;

    /**
     * Inserts a new User to the repository. This also validates if the
     * User is valid.
     *
     * @param user User to be added.
     * @return {@code true} if the User have been added, {@code false} otherwise.
     */
    boolean insert(E user) throws BusinessException, PersistenceException;

    /**
     * Deletes an User of the repository.
     *
     * @param user User to be deleted.
     * @return {@code true} if the User have been deleted, {@code false} otherwise.
     */
    boolean delete(E user) throws BusinessException, PersistenceException;

    /**
     * This change the email of the User.
     * Receives the User to be modified and the new email. This also check is the email is valid an unique.
     *
     * @param user     User to be modified.
     * @param newEmail User's new email.
     * @return {@code true} if the User have been modified, {@code false} otherwise.
     */
    boolean changeEmail(E user, String newEmail) throws BusinessException, PersistenceException;

    /**
     * This change the password of the User.
     * Receives the User to be modified and the new password. This also check is the old password
     * is correct.
     *
     * @param user        User to be modified.
     * @param newPassword User's new password.
     * @return {@code true} if the User have been modified, {@code false} otherwise.
     */
    boolean changePassword(E user, String newPassword) throws BusinessException, PersistenceException;

    /**
     * This change the name of the User.
     * Receives the User to be modified and the new firstname and lastname.
     *
     * @param user      User to be modified.
     * @param firstName User's new first name.
     * @param lastName  User's new last name.
     * @return {@code true} if the User have been modified, {@code false} otherwise.
     */
    boolean changeName(E user, String firstName, String lastName) throws BusinessException, PersistenceException;
}
