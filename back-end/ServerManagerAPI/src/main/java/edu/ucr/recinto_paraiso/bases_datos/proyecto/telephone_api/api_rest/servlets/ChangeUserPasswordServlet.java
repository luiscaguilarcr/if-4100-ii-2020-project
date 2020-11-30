package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.exceptions.SecurityException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.SessionVerification;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LoginSession;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.UserBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.UserBusinessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.util.Utility;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys.*;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HttpMethodsKeys.*;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates.jsonFormatErrorResponse;

/**
 * URI end-point: /user/name
 */
public class ChangeUserPasswordServlet extends HttpServlet {
    final String headersKeys = String.join(",", getInformation_headers(), user_id, session_token, email, password, new_password);
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        try {
            /* Session verification */
            final LoginSession loginSession = SessionVerification.getInstance().verifySessionToken(req);
            /* body */
            final Map<String, String> body = Utility.getBodyMap(req.getReader());
            final String email = body.get(HeadersKeys.email);
            final String password = body.get(HeadersKeys.password);
            final String newPassword = body.get(new_password);
            if (UserBusinessService.getInstance().changePassword(new UserBuilder()
                    .setId(loginSession.getUserId())
                    .setEmail(email)
                    .setPassword(password)
                    .setFirstName("")
                    .setLastName("")
                    .build(), newPassword)) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                throw new BusinessException("Password not changed. Verify the email and password.", SecurityException.USER_PASSWORD_NOT_VALID);
            }
        } catch (IOException ioException) {
            /* JSON FORMAT EXCEPTION */
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
            responseBuilder.setAllowMethods(PUT);
            responseBuilder.setAllowHeaders(headersKeys);
            responseBuilder.setExposeHeaders(headersKeys);
            responseBuilder.build();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        responseBuilder.setStatus(HttpServletResponse.SC_NO_CONTENT);
        responseBuilder.setAllowMethods(PUT, OPTIONS);
        responseBuilder.setAllowHeaders(String.join(",", Content_type, session_token, user_id));
        responseBuilder.setExposeHeaders(headersKeys);
        responseBuilder.build();
    }
}

