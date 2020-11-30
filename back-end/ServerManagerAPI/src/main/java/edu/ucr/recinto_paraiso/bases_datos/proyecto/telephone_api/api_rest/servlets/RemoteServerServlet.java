package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.exceptions.SecurityException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.LogProcessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.SessionVerification;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LoginSession;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.RemoteServer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.SSHConfiguration;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.RemoteServerBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.SSHConfigurationBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.RemoteServerBusinessService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys.*;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HttpMethodsKeys.*;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates.*;

/**
 * URI end-point: /user/remote_server/
 */
public class RemoteServerServlet extends HttpServlet {
    final String headersKeys = String.join(",", getInformation_headers(), getRemoteServerHeaders());

    /**
     * Use to insert a new remote server.
     *
     * @param req  request.
     * @param resp response.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        try {
            /* Session verification */
            final LoginSession loginSession = SessionVerification.getInstance().verifySessionToken(req);
            /* Body */
            final Map<String, String> body = Utility.getBodyMap(req.getReader());
            /* Create remote server */
            final RemoteServer remoteServer = ProcessRemoteServerRequest.createRemoteServer(body);
            remoteServer.setAdminId(loginSession.getUserId());
            /* Try insert */
            if (RemoteServerBusinessService.getInstance().insert(remoteServer)) {
                /* Remote server created */
                resourceCreatedResponse(responseBuilder);
                /* Log */
                LogProcessService.getInstance().genericRemoteServerLog(session_token, LogProcessService.getInstance().CONFIRMATION, "Servidor \"" + label + "\" agregado.");
            } else {
                /* Remote server not created */
                LogProcessService.getInstance().genericRemoteServerLog(session_token, LogProcessService.getInstance().ERROR, "Servidor \"" + label + "\" no agregado.");
                throw new BusinessException("Remote Server no added. ", BusinessException.SERVER_NOT_CREATED);
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
            responseBuilder.setAllowMethods(POST);
            responseBuilder.setAllowHeaders(headersKeys);
            responseBuilder.setExposeHeaders(headersKeys);
            responseBuilder.build();
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        try {
            /* Session verification */
            final LoginSession loginSession = SessionVerification.getInstance().verifySessionToken(req);
            /* Body */
            final Map<String, String> body = Utility.getBodyMap(req.getReader());
            /* Create remote server */
            final RemoteServer remoteServer = ProcessRemoteServerRequest.createRemoteServer(body);
            remoteServer.setAdminId(loginSession.getUserId());
            /* Try update */
            if (RemoteServerBusinessService.getInstance().update(remoteServer)){
                okResponse(responseBuilder);
                /* Log */
                LogProcessService.getInstance().genericUserLog(session_token, LogProcessService.getInstance().CONFIRMATION, "Servidor \"" + label + "\" modificado correctamente.");
            } else {
                throw new BusinessException("Remote Server no updated. ", BusinessException.SERVER_NOT_UPDATED);
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        try {
            /* Session verification */
            SessionVerification.getInstance().verifySessionToken(req);
            /* Headers */
            final Integer userId = req.getIntHeader(user_id);
            /* Get server list */
            List<RemoteServer> list = RemoteServerBusinessService.getInstance().getByUserId(userId);
            /* Parse response */
            List<String> json = new ArrayList<>(); //TODO modify remote server object to make JSON parse easier
            for (final RemoteServer i : list) {
                json.add(i.toJSON());
            }
            responseBuilder.setBody("[" + String.join(",", json) + "]");
            okResponse(responseBuilder);
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
            responseBuilder.setAllowMethods(GET);
            responseBuilder.setAllowHeaders(headersKeys);
            responseBuilder.setExposeHeaders(headersKeys);
            responseBuilder.build();
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        try {
            /* Session verification */
            SessionVerification.getInstance().verifySessionToken(req);
            /* Headers */
            final String serverId = req.getHeader(server_id);
            /* try delete */
            if (RemoteServerBusinessService.getInstance().delete(new RemoteServerBuilder()
                    .setId(Integer.parseInt(serverId))
                    .build())) {
                okResponse(responseBuilder);
                /* Log */
                LogProcessService.getInstance().genericUserLog(session_token, LogProcessService.getInstance().CONFIRMATION, "Servidor con ID \"" + serverId + "\" ha sido eliminado.");
            } else {
                throw new BusinessException("Remote server have not been deleted. Verify the id.", BusinessException.SERVER_NOT_DELETED);
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
            responseBuilder.setAllowMethods(GET);
            responseBuilder.setAllowHeaders(headersKeys);
            responseBuilder.setExposeHeaders(headersKeys);
            responseBuilder.build();
        }
    }
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        responseBuilder.setStatus(HttpServletResponse.SC_NO_CONTENT);
        responseBuilder.setAllowMethods(POST, OPTIONS, GET, PUT, DELETE);
        responseBuilder.setAllowHeaders(String.join(",", Content_type, session_token, user_id, server_id));
        responseBuilder.setExposeHeaders(headersKeys);
        responseBuilder.build();
    }
}

class ProcessRemoteServerRequest{
    static RemoteServer createRemoteServer(final Map<String, String> body){
        final String label = body.get(HeadersKeys.label);
        final String description = body.get(HeadersKeys.description);
        final String operativeSystem = body.get(operative_system);
        final String version = body.get(HeadersKeys.version);
        final String serverAddress = body.get(server_address);
        final String username = body.get(HeadersKeys.username);
        final String password = body.get(HeadersKeys.password);
        final String port = body.get(HeadersKeys.port);
        final String sessionTime = body.get(session_time);
        /* Build Remote Server */
        final SSHConfiguration sshConfiguration = new SSHConfigurationBuilder()
                .setServerAddress(serverAddress)
                .setUsername(username)
                .setPassword(password)
                .setPort(Integer.parseInt(port))
                .setSessionTime(Integer.parseInt(sessionTime))
                .build();
        return new RemoteServerBuilder()
                .setLabel(label)
                .setDescription(description)
                .setOperativeSystem(operativeSystem)
                .setVersion(version)
                .setSshConfiguration(sshConfiguration)
                .build();
    }
}