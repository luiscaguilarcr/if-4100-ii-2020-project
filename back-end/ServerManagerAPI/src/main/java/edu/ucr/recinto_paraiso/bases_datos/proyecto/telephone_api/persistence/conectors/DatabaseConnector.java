package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnector {
    Connection getConnection() throws SQLException;
}
