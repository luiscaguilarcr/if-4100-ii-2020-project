package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors;

/**
 * This contains a principal connector with the UCR Turrialba MSQLS Instance.
 * Also can generate more connectors using the method generateConnector();
 */
public class UCRConnector {
    private static UCRConnector instance;
    private final MSQLSConnector connector;

    private static final String IP = "163.178.107.10:1433";
    private static final String DATABASE_NAME = "B88782_B90127_B90514";
    private static final String USER = "laboratorios";
    private static final String PASSWORD = "KmZpo.2796";

    /**
     * Creates the principal connector that will be stored.
     */
    private UCRConnector() {
        connector = new MSQLSConnector(IP, DATABASE_NAME, USER, PASSWORD);
    }

    /**git add .
     * Returns the instance of the class.
     *
     * @return {@code UCRConnector} instance of the class.
     */
    public static UCRConnector getInstance() {
        if (instance == null) {
            instance = new UCRConnector();
        }
        return instance;
    }

    /**
     * Returns the principal connector that is stored in the class.
     *
     * @return {@code MSQLSConnector} Connector with the MSQLS database.
     */
    public MSQLSConnector getConnector() {
        return connector;
    }

    /**
     * Generates and returns a MSQLS database connector. This will not be stored in this class.
     *
     * @return {@code MSQLSConnector} Connector with the MSQLS database.
     */
    public MSQLSConnector generateConnector() {
        return new MSQLSConnector(IP, DATABASE_NAME, USER, PASSWORD);
    }
}
