package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This service is used to establish a connection with Student's UCR database.
 */
public class UCRDatabaseService implements DatabaseService {
    private final DatabaseConnector connector;
    private Connection connection;

    /**
     * Initialize the service.
     *
     * True to create an exclusive connector, false to reuse or create one if not exist.
     * @param generate Generate a new Connector with the database.
     */
    public UCRDatabaseService(final boolean generate) {
        this.connector = generate ?
                UCRConnector.getInstance().generateConnector() :
                UCRConnector.getInstance().getConnector();
    }


    /**
     * Establish a connection with the database.
     *
     * @return {@code True} is the connection is establish,
     * {@code False} otherwise.
     * @throws PersistenceException Caused if the connection
     *                                  haven't been established.
     */
    @Override
    public boolean connect() throws PersistenceException {
        try {
            if (this.connection == null) {
                this.connection = this.connector.getConnection();
            }
            return this.connection != null;
        } catch (SQLException exception) {
            throw new PersistenceException("The connection with database failed.\nDetails: " + exception.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }

    /**
     * Finish the connection with the database.
     *
     * @throws PersistenceException Caused if the connection haven't been previous
     *                                  established or is all ready closed.
     */
    @Override
    public void disconnect() throws PersistenceException {
        try {
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
        } catch (SQLException exception) {
            throw new PersistenceException("Error during close connection of database.\nDetails: " + exception.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        }
    }
    @Override
    public Connection getConnection() {
        return connection;
    }
}
