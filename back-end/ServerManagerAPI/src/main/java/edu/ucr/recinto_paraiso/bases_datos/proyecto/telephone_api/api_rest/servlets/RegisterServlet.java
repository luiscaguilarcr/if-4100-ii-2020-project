package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.exceptions.SecurityException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.ApiClientVerification;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.LoginProcessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.util.Utility;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HttpMethodsKeys;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys.*;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates.*;

/**
 * URI end-point: /user/register
 */
public class RegisterServlet extends HttpServlet {
    final String headersKeys = String.join(",", getInformation_headers(), client_token);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        try {
            /* Headers */
            final String clientToken = req.getHeader(client_token);
            final Map<String, String> body = Utility.getBodyMap(req.getReader());
            /* Body */
            final String email = body.get(HeadersKeys.email);
            final String password = body.get(HeadersKeys.password);
            final String firstName = body.get(HeadersKeys.first_name);
            final String lastName = body.get(HeadersKeys.last_name);
            /* Verify clientToken */
            ApiClientVerification.getInstance().verifyClientToken(clientToken);
            /* Try register */
            if (LoginProcessService.getInstance().register(email, password, firstName, lastName)) {
                /* User created */
                userCreatedResponse(responseBuilder);
            } else {
                /* User not created */
                throw new BusinessException("User not created", BusinessException.USER_CREATION_ERROR);
            }
        } catch (IOException ioException) {
            /* JSON format exception */
            jsonFormatErrorResponse(responseBuilder);
        } catch (SecurityException exception) {
            /* Security Exception */
            ResponseTemplates.securityExceptionResponse(responseBuilder, exception);
        } catch (BusinessException exception) {
            /* Business Exception */
            ResponseTemplates.businessExceptionResponse(responseBuilder, exception);
        } catch (PersistenceException exception) {
            /* Persistence Exception */
            ResponseTemplates.persistenceExceptionResponse(responseBuilder, exception);
        } finally {
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
        responseBuilder.setAllowHeaders(String.join(",", Content_type, client_token));
        responseBuilder.setExposeHeaders(headersKeys);
        responseBuilder.build();
    }
}
