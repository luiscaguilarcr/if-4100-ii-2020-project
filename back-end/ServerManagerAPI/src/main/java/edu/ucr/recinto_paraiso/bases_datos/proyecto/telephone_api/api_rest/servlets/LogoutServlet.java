package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.exceptions.SecurityException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.LogProcessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.LoginProcessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.SessionVerification;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LoginSession;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys.*;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HttpMethodsKeys.*;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates.*;

/**
 * URI end-point: /user/logout
 */
public class LogoutServlet extends HttpServlet {
    final String headersKeys = String.join(",", getInformation_headers(), getRemoteServerHeaders());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        try {
            /* Session verification */
            final LoginSession loginSession = SessionVerification.getInstance().verifySessionToken(req);
            /* Try logout */
            if (LoginProcessService.getInstance().logout(loginSession)) {
                okResponse(responseBuilder);
                /* Log */
                LogProcessService.getInstance().genericUserLog(session_token, LogProcessService.getInstance().INFORMATION, "Finalizó la sesión.");
            } else {
                throw new SecurityException("Login session not closed", SecurityException.LOGOUT_ERROR);
            }
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
            responseBuilder.setAllowMethods(POST);
            responseBuilder.setAllowHeaders(headersKeys);
            responseBuilder.setExposeHeaders(headersKeys);
            responseBuilder.build();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        responseBuilder.setStatus(HttpServletResponse.SC_NO_CONTENT);
        responseBuilder.setAllowMethods(POST, OPTIONS);
        responseBuilder.setAllowHeaders(String.join(",", Content_type, session_token, user_id));
        responseBuilder.setExposeHeaders(headersKeys);
        responseBuilder.build();
    }
}
