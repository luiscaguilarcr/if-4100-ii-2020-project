package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;

import java.util.List;

/**
 * This is an line interface, that defines the schema required to work with user objects.
 */
public interface LineService<E> {
    /**
     * Search and returns an User by it's username.
     *
     * @return {@code User} if have been found. {@code null} if doesn't exist
     * an User with the username provided.
     */
    List<E> get() throws BusinessException, PersistenceException;

    /**
     * Inserts a new User to the repository. This also validates if the
     * User is valid.
     *
     * @param line Line.
     */
    void insert(E line) throws BusinessException, PersistenceException;

    /**
     * Inserts a new User to the repository. This also validates if the
     * User is valid.
     *
     * @param line Line.
     * @return {@code true} if the User have been added, {@code false} otherwise.
     */
    void update(E line) throws BusinessException, PersistenceException;

    /**
     * Deletes an User of the repository.
     *
     * @param line User to be deleted.
     * @return {@code true} if the User have been deleted, {@code false} otherwise.
     */
    boolean delete(E line) throws BusinessException, PersistenceException;
}
