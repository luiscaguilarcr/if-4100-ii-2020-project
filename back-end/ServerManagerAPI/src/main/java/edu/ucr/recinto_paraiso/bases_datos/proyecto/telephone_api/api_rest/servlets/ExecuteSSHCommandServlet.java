package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HttpMethodsKeys;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.exceptions.SecurityException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.SessionVerification;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.SSHCommandRequest;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.SshBusinessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.util.Utility;
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
 * URI path: /user/remote_server/ssh/execute_command
 */
public class ExecuteSSHCommandServlet extends HttpServlet {
    final String headersKeys = String.join(",", getInformation_headers(), user_id, session_token, ssh_token, command_response, command_response_type, command);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        try {
            /* Session verification */
            SessionVerification.getInstance().verifySessionToken(req);
            SessionVerification.getInstance().verifySshSessionToken(req);// TODO implement
            /* Headers */
            final String SSH_TOKEN = req.getHeader(ssh_token);
            /* Body */
            final Map<String, String> body = Utility.getBodyMap(req.getReader());
            final String COMMAND = body.get(command);
            /* Build command request */
            SSHCommandRequest<String> sshCommandRequest = new SSHCommandRequest<>(SSH_TOKEN);
            if (COMMAND == null || COMMAND.equals("")) {
                throw new BusinessException("The command is not provided.", BusinessException.SSH_COMMAND_NOT_PROVIDED);
            }
            sshCommandRequest.setCommand(COMMAND);
            sshCommandRequest = SshBusinessService.getInstance().executeCommand(sshCommandRequest);

            if (sshCommandRequest != null) {
                sshCommandExecutionResponse(responseBuilder, sshCommandRequest);
            } else {
                throw new BusinessException("Command not executed.", BusinessException.SSH_SESSION_COMMAND_EXECUTION_FAILED);
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
        responseBuilder.setAllowHeaders(String.join(",", Content_type, session_token, user_id, ssh_token, command));
        responseBuilder.setExposeHeaders(headersKeys);
        responseBuilder.build();
    }
}
