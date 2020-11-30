package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HttpMethodsKeys;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.exceptions.SecurityException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.ApiClientVerification;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.TokenService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.util.Utility;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates.jsonFormatErrorResponse;

/**
 * URI End-point: /authorize
 */
public class AuthorizationServlet extends HttpServlet {
    final String headersKeys = String.join(",", HeadersKeys.client_token, HeadersKeys.Content_type, HeadersKeys.Authorization);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        try {
            /* Body */
            final Map<String, String> body = Utility.getBodyMap(req.getReader());
            final String apiToken = body.get(HeadersKeys.api_token);
            /* Verify API-TOKEN */
            ApiClientVerification.getInstance().verifyApiToken(apiToken);
            /* Get CLIENT-TOKEN */
            final String clientToken = TokenService.getInstance().getClientToken(apiToken);
            /* Add response information */
            ResponseTemplates.validApiTokenResponse(responseBuilder, clientToken);
        } catch (IOException ioException) {
            /* JSON FORMAT EXCEPTION */
            jsonFormatErrorResponse(responseBuilder);
        } catch (SecurityException exception) {
            /* Security Exception */
            ResponseTemplates.securityExceptionResponse(responseBuilder, exception);
        } finally {
            /* Build response */
            responseBuilder.setAllowMethods(HttpMethodsKeys.POST);
            responseBuilder.setAllowHeaders(headersKeys);
            responseBuilder.setExposeHeaders(headersKeys);
            responseBuilder.build();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        responseBuilder.setStatus(HttpServletResponse.SC_NO_CONTENT);
        responseBuilder.setAllowMethods(HttpMethodsKeys.POST, HttpMethodsKeys.OPTIONS);
        responseBuilder.setAllowHeaders(HeadersKeys.Content_type);
        responseBuilder.setAllowHeaders(headersKeys);
        responseBuilder.setExposeHeaders(headersKeys);
        responseBuilder.build();
    }
}
