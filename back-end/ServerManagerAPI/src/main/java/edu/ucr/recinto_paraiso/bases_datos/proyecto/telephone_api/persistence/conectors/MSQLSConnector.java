package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to establish the connection with a Microsoft SQL Serverdatabase..
 */
class MSQLSConnector implements DatabaseConnector {

    /* CONFIGURATION */
    private final String URL;
    private final String USER;
    private final String PASSWORD;

    /**
     * Create a connector to access into a microsoft sql server database.
     *
     * @param IP of the database. Example "ip:port".
     * @param USER username that has access to the database.
     * @param PASSWORD password to access.
     */
    public MSQLSConnector(String IP, String DATABASE_NAME, String USER, String PASSWORD){
        this.URL = "jdbc:sqlserver://" + IP + ";databaseName=" + DATABASE_NAME + ";";
        this.USER = USER;
        this.PASSWORD = PASSWORD;
    }
    /**
     * This method establish the connection with the database
     *
     * @return {@code Connection} connection with DB, {@code null} error.
     * @throws SQLException when an error happened during DB connection.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
