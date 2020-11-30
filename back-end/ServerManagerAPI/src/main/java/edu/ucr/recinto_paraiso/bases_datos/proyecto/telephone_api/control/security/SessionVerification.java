package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LoginSession;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.SshBusinessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.exceptions.SecurityException;

import javax.servlet.http.HttpServletRequest;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys.*;

public final class SessionVerification {
    private static SessionVerification instance;

    private SessionVerification() {
    }

    public static SessionVerification getInstance() {
        if (instance == null) {
            instance = new SessionVerification();
        }
        return instance;
    }

    public LoginSession verifySessionToken(final HttpServletRequest req) throws SecurityException {
        /* Header */
        final String sessionToken = req.getHeader(session_token);
        final String ip = req.getRemoteAddr();
        final Integer userId = req.getIntHeader(user_id);
        if (sessionToken == null || sessionToken.equals("")) {
            /* Verify sessionToken */
            throw new SecurityException("[SESSION-TOKEN] not provided", SecurityException.SESSION_TOKEN_NOT_PROVIDED);
        }
        /* Verify login session */
        final LoginSession loginSession = new LoginSession(userId, sessionToken, ip);
        if (LoginProcessService.getInstance().validateSession(loginSession)) {
            return loginSession;
        }
        throw new SecurityException("[SESSION-TOKEN] not valid.", SecurityException.SESSION_TOKEN_NOT_VALID);
    }
    public String verifySshSessionToken(final HttpServletRequest req) throws SecurityException, PersistenceException, BusinessException {
        /* Header */
        final String sshToken = req.getHeader(ssh_token);
        if (sshToken == null || sshToken.equals("")) {
            /* Verify sessionToken */
            throw new SecurityException("[SSH-TOKEN] not provided", SecurityException.SSH_TOKEN_NOT_PROVIDED);
        }
        /* Verify ssh session */
        if( SshBusinessService.getInstance().isSessionActive(sshToken) ){
            return sshToken;
        }
        throw new SecurityException("[SSH-TOKEN] not active.", SecurityException.SSH_TOKEN_NOT_VALID);
    }
}
