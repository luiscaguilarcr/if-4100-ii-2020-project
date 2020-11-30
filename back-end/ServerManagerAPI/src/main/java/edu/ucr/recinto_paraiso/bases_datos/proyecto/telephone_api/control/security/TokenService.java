package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors.DatabaseService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.conectors.UCRDatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TokenService {
    private static TokenService instance;
    private final Map<String, String> clientList; /* api_token, client_token */
    /* Error codes */
    public static final int API_TOKEN_NOT_PROVIDED = 10;
    public static final int SESSION_TOKEN_NOT_PROVIDED = 30;
    public static final int SESSION_TOKEN_INVALID = 31;
    /* Database connector */
    private final DatabaseService databaseService;
    /* Constructor */
    private TokenService(DatabaseService databaseService) {
        this.databaseService = databaseService;
        this.clientList = getClientList();
    }
    /* Instance */
    public static TokenService getInstance() {
        if(instance == null){
            instance = new TokenService(new UCRDatabaseService(false));
        }
        return instance;
    }
    /**
     * Get the client using the [API-TOKEN] as identifier.
     * @param apiToken [API-TOKEN]
     * @return String [CLIENT-TOKEN] or null in case of unknown [API-TOKEN].
     */
    public String getClientToken(final String apiToken){
        /* VERIFY THE CLIENT */
        if (clientList.containsKey(apiToken)){
            /* API_TOKEN AUTHORIZE */
            return clientList.get(apiToken);
        }
        /* API_TOKEN UNKNOWN */
        return null;
    }
    /**
     * Verify if the client token is valid.
     *
     * @param clientToken clientToken;
     * @return {boolean} if it's authorize.
     */
    public boolean invalidClientToken(String clientToken){
        return !clientList.containsValue(clientToken);
    }
    /**
     * Verify if the token is an authorize client.
     *
     * @param apiToken apiToken.
     * @return {boolean} if exists.
     */
    public boolean verifyApiToken(String apiToken){
        return clientList.containsKey(apiToken);
    }
    /**
     * Get the list of authorize clients for database.
     * @return Map with the client's list.
     */
    private Map<String, String> getClientList(){
        ResultSet resultSet;
        final Map<String, String> map = new HashMap<>();
        try {
            /* Create statement */
            final String getStatement = "SELECT [client_token], [api_token] FROM [ApiClient] WHERE [active] = 1;";
            databaseService.connect();
            final PreparedStatement preparedStatement = databaseService.getConnection().prepareStatement(getStatement);
            resultSet = preparedStatement.executeQuery();
            /* Read all rows */
            while (resultSet.next()) {
                /* Add into map */
                map.put( resultSet.getString("api_token"),
                          resultSet.getString("client_token"));
            }
        } catch (SQLException e) {
            //throw new PersistenceException("Error during get execution. Details: " + e.getMessage(), PersistenceException.DATABASE_CONNECTION_FAILED);
        } catch (PersistenceException exception) {

        }

        return map;
    }
}

