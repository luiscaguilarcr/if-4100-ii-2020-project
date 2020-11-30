package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.exceptions.SecurityException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.ApiClientVerification;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.LogProcessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.LoginProcessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LoginSession;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.User;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.UserBusinessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.util.Utility;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HttpMethodsKeys;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys.*;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates.*;

/**
 * URI path: /user/login
 */
public class LoginServlet extends HttpServlet {
    final String headersKeys = String.join(",", getInformation_headers(), session_token, expire_time, user);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        try {
            /* Headers */
            final String clientToken = req.getHeader(client_token);
            /* Body */
            final Map<String, String> body = Utility.getBodyMap(req.getReader());
            final String email = body.get(HeadersKeys.email);
            final String password = body.get(HeadersKeys.password);
            final String ip = req.getRemoteAddr();
            /* Verify clientToken */
            ApiClientVerification.getInstance().verifyClientToken(clientToken);
            /* Try login */
            final LoginSession loginSession = LoginProcessService.getInstance().login(email, password, ip);
            if (loginSession != null) {
                /* Valid login */
                final User user = UserBusinessService.getInstance().get(email);
                /* Process response */
                validLoginResponse(responseBuilder, loginSession, user);
                /* Log */ //TODO do refactor
                LogProcessService.getInstance().sessionTokenGenerationLog(clientToken, loginSession.getSessionToken(), user.getId(), ip);
                LogProcessService.getInstance().genericUserLog(loginSession.getSessionToken(), LogProcessService.getInstance().INFORMATION, "Inicio de sesión.");
            } else {
                throw new SecurityException("[SESSION-TOKEN] not valid.", SecurityException.SESSION_TOKEN_NOT_VALID);
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
        responseBuilder.setAllowHeaders(String.join(",", Content_type, client_token, session_token));
        responseBuilder.setExposeHeaders(headersKeys);
        responseBuilder.build();
    }
}
