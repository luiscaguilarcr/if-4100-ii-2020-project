package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;

import javax.servlet.http.HttpServletResponse;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException.JSON_FORMAT_EXCEPTION;

public class ResponseTemplates {
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
    public static void jsonFormatErrorResponse(final ResponseBuilder responseBuilder){
        /* INVALID JSON FORMAT */
        responseBuilder.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        responseBuilder.setValid_authorization(false);
        responseBuilder.setErrorMessage("INVALID JSON FORMAT.");
        responseBuilder.setErrorCode(JSON_FORMAT_EXCEPTION);
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
}

