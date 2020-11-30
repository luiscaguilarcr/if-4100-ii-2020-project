package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import java.util.List;

public interface CallService<E>  {
    /**
     * Search and returns an call by it's telephone number.
     *
     * @return {@code Call} if have been found. {@code null} if doesn't exist
     * an Call with the telephone number provided.
     */
    List<E> get() throws BusinessException, PersistenceException;

    /**
     * Inserts a new Call to the repository. This also validates if the
     * Call is valid.
     *
     * @param call Call to be added.
     */
    void insert(E call) throws BusinessException, PersistenceException;

    /**
     * Update a new Call to the repository. This also validates if the
     * Call is valid.
     *
     * @param call Call to be updated.
     * @return {@code true} if the User have been added, {@code false} otherwise.
     */
    void update(E call) throws BusinessException, PersistenceException;

    /**
     * Deletes an Call of the repository.
     *
     * @param call Call to be deleted.
     * @return {@code true} if the User have been deleted, {@code false} otherwise.
     */
    boolean delete(E call) throws BusinessException, PersistenceException;
}
