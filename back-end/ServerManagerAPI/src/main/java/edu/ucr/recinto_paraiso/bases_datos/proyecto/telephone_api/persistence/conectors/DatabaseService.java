package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;

import java.sql.Connection;

public interface DatabaseService {
    /**
     * Establish a connection with the database.
     *
     * @return {@code True} is the connection is establish,
     * {@code False} otherwise.
     * @throws PersistenceException Caused if the connection
     *                                  haven't been established.
     */
    boolean connect() throws PersistenceException;
    /**
     * Finish the connection with the database.
     *
     * @throws PersistenceException Caused if the connection haven't been previous
     *                                  established or is all ready closed.
     */
    void disconnect() throws PersistenceException;

    Connection getConnection();
}
