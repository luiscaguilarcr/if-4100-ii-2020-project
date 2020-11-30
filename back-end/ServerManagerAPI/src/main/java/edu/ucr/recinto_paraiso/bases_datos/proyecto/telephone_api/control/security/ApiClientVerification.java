package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.exceptions.SecurityException;

/**
 * Validate if the Client app is authorize for use this API
 *
 */
public final class ApiClientVerification {
    private static ApiClientVerification instance;

    private ApiClientVerification(){ }

    public static ApiClientVerification getInstance(){
        if (instance == null){
            instance = new ApiClientVerification();
        }
        return instance;
    }
    public void verifyApiToken(final String apiToken) throws SecurityException {
        if (apiToken == null || apiToken.equals("")) {
            /* API-TOKEN NOT PROVIDED */
            throw new SecurityException("[API-TOKEN] not provided", SecurityException.API_TOKEN_NOT_PROVIDED);
        }
        if (!TokenService.getInstance().verifyApiToken(apiToken)) {
            /* INVALID API-TOKEN */
            throw new SecurityException("[API-TOKEN] not valid", SecurityException.API_TOKEN_NOT_VALID);
        }
    }

    public void verifyClientToken(final String clientToken) throws SecurityException {
        if (clientToken == null || clientToken.equals("")) {
            /* CLIENT-TOKEN NOT PROVIDED */
            throw new SecurityException("[CLIENT-TOKEN] not provided", SecurityException.CLIENT_TOKEN_NOT_PROVIDED);
        }
        if (TokenService.getInstance().invalidClientToken(clientToken)) {
            /* INVALID CLIENT-TOKEN */
            throw new SecurityException("[CLIENT-TOKEN] not valid", SecurityException.CLIENT_TOKEN_NOT_VALID);
        }
    }
}
