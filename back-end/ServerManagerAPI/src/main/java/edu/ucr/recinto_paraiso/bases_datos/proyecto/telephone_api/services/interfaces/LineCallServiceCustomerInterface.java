package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;

import java.util.List;

public interface LineCallServiceCustomerInterface<E, K, S> {
    /**
     * Search and returns all the line call service customer.
     *
     * @return {@code Call} if have been found. {@code null} if doesn't exist
     * an Call with the telephone number provided.
     */
    List<E> getAll() throws BusinessException, PersistenceException;

    /**
     * Search and returns an line call service customer by it's service name and status.
     *
     * @return {@code Call} if have been found. {@code null} if doesn't exist
     * an Call with the telephone number provided.
     */
    List<E> get(K serviceName, S status) throws BusinessException, PersistenceException;
}
