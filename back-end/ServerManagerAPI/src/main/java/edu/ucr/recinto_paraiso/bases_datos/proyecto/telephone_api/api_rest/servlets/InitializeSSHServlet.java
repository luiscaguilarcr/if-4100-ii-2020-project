package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.exceptions.SecurityException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.LogProcessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.SessionVerification;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LoginSession;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.RemoteServer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.RemoteServerBusinessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.SshBusinessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.util.Utility;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys.*;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys.server_id;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HttpMethodsKeys.*;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates.*;

/**
 * URI path: /user/remote_server/ssh/init
 */
public class InitializeSSHServlet extends HttpServlet {
    final String headersKeys = String.join(",", getInformation_headers(), user_id, session_token, ssh_token);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        try {
            /* Session verification */
            final LoginSession loginSession = SessionVerification.getInstance().verifySessionToken(req);
            /* Body */
            final Map<String, String> body = Utility.getBodyMap(req.getReader());
            final String serverId = body.get(server_id);
            final String username = body.get(HeadersKeys.username);
            final String password = body.get(HeadersKeys.password);
            /* Verify server id */ // TODO do in lower layer.
            if (serverId == null || serverId.equals("")) {
                throw new SecurityException("Remote server id is not provided", BusinessException.SERVER_ID_NOT_PROVIDED);
            }
            /* Get the server */
            final RemoteServer remoteServer = RemoteServerBusinessService.getInstance()
                    .getServer(loginSession.getUserId(), Integer.parseInt(serverId));
            /* Validate if the server exists */
            if (remoteServer != null) {
                /* Verify if the username is stored */
                if (remoteServer.getSshConfiguration().getUsername() == null ||
                        remoteServer.getSshConfiguration().getUsername().equals("")) {
                    /* Use the provided in the request */
                    remoteServer.getSshConfiguration().setUsername(username);
                }
                /* Verify if the password is stored */
                if (remoteServer.getSshConfiguration().getPassword() == null ||
                        remoteServer.getSshConfiguration().getPassword().equals("")) {
                    /* Use the provided in the request */
                    remoteServer.getSshConfiguration().setPassword(password);
                }
                /* Establish the connection */
                final String sshToken = SshBusinessService.getInstance()
                        .startSession(remoteServer); //TODO considerate a loop thread in case of failure.
                if (sshToken != null) {
                    /* Build response */
                    validSSHSessionResponse(responseBuilder, sshToken, remoteServer.getSshConfiguration().getSessionTime());
                    /* Log */
                    LogProcessService.getInstance().sshTokenGenerationLog(loginSession.getSessionToken(), sshToken, remoteServer.getId());
                } else {
                    throw new SecurityException("SSH Token generation failed. Try again later, verify server connection.", BusinessException.SSH_SESSION_CONNECTION_FAILED);
                }
            } else {
                throw new BusinessException("Remote server with id " + serverId + " is not found. Verify the id or add the server.", BusinessException.SERVER_ID_NOT_FOUND);
            }
        } catch (IOException | NumberFormatException exception) {
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


