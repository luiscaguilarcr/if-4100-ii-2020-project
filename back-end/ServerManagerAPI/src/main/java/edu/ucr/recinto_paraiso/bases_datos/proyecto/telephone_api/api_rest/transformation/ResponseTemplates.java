package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.exceptions.SecurityException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.TokenService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LoginSession;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.SSHCommandRequest;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.User;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;

import javax.servlet.http.HttpServletResponse;
public class ResponseTemplates {


    /**
     * Handles http response when a security exception is present during a request.
     *
     * @param responseBuilder response builder.
     * @param exception Security exception.
     */
    public static void securityExceptionResponse(final ResponseBuilder responseBuilder, final SecurityException exception){
        responseBuilder.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        responseBuilder.setValid_authorization(false);
        responseBuilder.setErrorMessage(exception.getMessage());
        responseBuilder.setErrorCode(exception.getCode());
    }

    /**
     * Handles http response when a business exception is present during a request.
     *
     * @param responseBuilder response builder.
     * @param exception Business exception.
     */
    public static void businessExceptionResponse(final ResponseBuilder responseBuilder, final BusinessException exception){
        responseBuilder.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        responseBuilder.setValid_authorization(true);
        responseBuilder.setErrorMessage(exception.getMessage());
        responseBuilder.setErrorCode(exception.getCode());
    }
    /**
     * Handles http response when a persistence exception is present during a request.
     *
     * @param responseBuilder response builder.
     * @param exception Persistence exception.
     */
    public static void persistenceExceptionResponse(final ResponseBuilder responseBuilder, final PersistenceException exception){
        responseBuilder.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        responseBuilder.setValid_authorization(true);
        responseBuilder.setErrorMessage(exception.getMessage());
        responseBuilder.setErrorCode(exception.getCode());
    }
    public static void validApiTokenResponse(final ResponseBuilder responseBuilder, final String clientToken){
        /* INVALID API-TOKEN */
        responseBuilder.setStatus(HttpServletResponse.SC_OK);
        responseBuilder.setValid_authorization(true);
        responseBuilder.setHeader(HeadersKeys.client_token, clientToken);
    }
    public static void jsonFormatErrorResponse(final ResponseBuilder responseBuilder){
        /* INVALID JSON FORMAT */
        responseBuilder.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        responseBuilder.setValid_authorization(false);
        responseBuilder.setErrorMessage("INVALID JSON FORMAT.");
        responseBuilder.setErrorCode(TokenService.API_TOKEN_NOT_PROVIDED);
    }
    public static void userCreatedResponse(final ResponseBuilder responseBuilder){
        /* USER CREATED */
        responseBuilder.setStatus(HttpServletResponse.SC_CREATED);
        responseBuilder.setCompleted(true);
    }
    public static void validLoginResponse(final ResponseBuilder responseBuilder, final LoginSession loginSession, final User user){
        /* VALID LOGIN */
        responseBuilder.setStatus(HttpServletResponse.SC_OK);
        responseBuilder.setValid_authorization(true);
        responseBuilder.setCompleted(true);
        responseBuilder.setHeader(HeadersKeys.session_token, loginSession.getSessionToken());
        responseBuilder.setHeader(HeadersKeys.expire_time, loginSession.getExpireDate().toString());
        responseBuilder.setHeader(HeadersKeys.user, user.toJSON());
    }
    public static void resourceCreatedResponse(final ResponseBuilder responseBuilder){
        /* RESOURCE CREATED RESPONSE */
        responseBuilder.setStatus(HttpServletResponse.SC_CREATED);
        responseBuilder.setValid_authorization(true);
        responseBuilder.setCompleted(true);
    }
    public static void okResponse(final ResponseBuilder responseBuilder){
        /* RESOURCE CREATED RESPONSE */
        responseBuilder.setStatus(HttpServletResponse.SC_OK);
        responseBuilder.setValid_authorization(true);
        responseBuilder.setCompleted(true);
    }
    public static void validSSHSessionResponse(final ResponseBuilder responseBuilder, final String sshToken, final Integer expireTime) {
        /* SSH SESSION RESPONSE */
        responseBuilder.setStatus(HttpServletResponse.SC_OK);
        responseBuilder.setHeader(HeadersKeys.ssh_token, sshToken);
        responseBuilder.setIntHeader(HeadersKeys.expire_time, expireTime);
        responseBuilder.setValid_authorization(true);
        responseBuilder.setCompleted(true);
    }
    public static void sshCommandExecutionResponse(final ResponseBuilder responseBuilder, final SSHCommandRequest<String> sshCommandRequest){
        /* SSH SERVICE EXCEPTION RESPONSE */
        responseBuilder.setStatus(HttpServletResponse.SC_OK);
        responseBuilder.setValid_authorization(true);
        responseBuilder.setCompleted(true);
        responseBuilder.setHeader(HeadersKeys.command_response, sshCommandRequest.getResponse());
    }
}

