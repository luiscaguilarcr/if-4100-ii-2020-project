package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.transformation;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.LogProcessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.RemoteServer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.SSHCommandRequest;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.SSHSession;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.SshService;

import java.util.ArrayList;
import java.util.List;

/**
 * Default access
 * <p>
 * Implements Singleton Pattern.
 */
public class SshPersistenceService implements SshService<RemoteServer, String, SSHCommandRequest<String>> {
    /* Instance */
    private static SshPersistenceService instance;
    private final List<SSHSession> sshSessionList; /* List of active SSH sessions */
    /* Constructor */
    private SshPersistenceService(List<SSHSession> sshSessionList) {
        this.sshSessionList = sshSessionList;
    }
    /**
     * Returns the instance of the class.
     *
     * @return {@code SshPersistenceService} Instance of the class.
     */
    public static SshPersistenceService getInstance() {
        if (instance == null) {
            instance = new SshPersistenceService(new ArrayList<>());
        }
        return instance;
    }
    /**
     * Begins a new SSH Session with the remote server provided.
     * <p>
     * When the connection is establish a Session key will be generate.
     * This key is used to identify the session as unique, execute commands
     * ask for the status and end the session any time.
     *
     * @param remoteServer Remote Server to establish the connection.
     * @return {@code K} returns an unique key Session if the connection
     * have been establish {@code null} if the connection failed.
     * @throws PersistenceException Exception threw when a service problem has happened.
     *                             Can be cause of an invalid Server object or SSH
     *                             Configuration provided.
     *                             Also can be caused of network problems tha waiting time
     *                             expires during the intent to accomplish the remote server.
     */
    @Override
    public String startSession(final RemoteServer remoteServer) throws BusinessException, PersistenceException {
        /* Create session */
        final SSHSession sshSession = new SSHSession(remoteServer);
        /* Establish connection */
        if (sshSession.connect()) {
            if (sshSession.isConnected()) {
                sshSessionList.add(sshSession);
                return sshSession.getId();
            }
        }
        throw new PersistenceException("Error when was trying to establish the SSH session.", BusinessException.SSH_SESSION_CONNECTION_FAILED);
    }
    /**
     * Ends an active SSH Session.
     * <p>
     * This methods will end the SSH Session that identify by the Session Key provided.
     *
     * @param sessionKey Session Key to identify the session.
     * @return {@code true} if the session has been close successfully {@code false} if
     * an error happened and the session is still open.
     * @throws PersistenceException Exception threw when a service problem has happened.
     *                             Can be cause of an invalid or unknown session key.
     *                             Also can be caused of network problems tha waiting time
     *                             expires during the intent to accomplish the remote server.
     */
    @Override
    public boolean endSession(final String sessionKey) throws PersistenceException {
        /* Verify token */
        if(sessionKey==null){
            throw new PersistenceException("SSH Session id is not provided.", BusinessException.SSH_TOKEN_NOT_PROVIDED);
        }
        /* Search in sessions list */
        for (SSHSession sshSession : sshSessionList) {
            if (sshSession.getId().equals(sessionKey)) {
                /* Session found */
                sshSession.disconnect();
                if (sshSessionList.remove(sshSession)){
                    /* Log */
                    LogProcessService.getInstance().genericSSHConnectionLog(sessionKey,
                            LogProcessService.getInstance().CONFIRMATION,
                            "Sesión finalizada.");
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Ends the active SSH Sessions of one user.
     * <p>
     * This methods will end the SSH Session that identify by the Session Key provided.
     *
     * @param userId User id identify the all the sessions related within.
     * @return {@code true} if the session has been close successfully {@code false} if
     * an error happened and the session is still open.
     * @throws PersistenceException Exception threw when a service problem has happened.
     *                             Can be cause of an invalid or unknown session key.
     *                             Also can be caused of network problems tha waiting time
     *                             expires during the intent to accomplish the remote server.
     */
    @Override
    public boolean endSessions(Integer userId) throws PersistenceException {
        try {
            /* Search in session list */
            for (int i = 0; i < sshSessionList.size(); i ++) {
                final SSHSession sshSession = sshSessionList.get(i);
                if (sshSession.getRemoteServer().getAdminId().equals(userId)) {
                    /* Session found */
                    sshSession.disconnect();
                    if (sshSessionList.remove(sshSession)) {
                        /* log */
                        LogProcessService.getInstance().genericSSHConnectionLog(sshSession.getId(),
                                LogProcessService.getInstance().CONFIRMATION,
                                "Sesión finalizada.");
                    } else {
                        throw new PersistenceException("SSH Session wasn't deleted.", BusinessException.END_SSH_SESSION_CONNECTION_FAILED);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            throw new PersistenceException("SSH Session service exception. Details:" + e.getMessage(), BusinessException.END_SSH_SESSION_CONNECTION_FAILED );
        }
    }
    /**
     * Executes a SSH Command.
     * <p>
     * This methods will executes a SSH Session using the Session Key provided to identify
     * the server.
     *
     * @param command SSH Command to be executed.
     * @return {@code R} Response provided by the Remote Server {@code null} if the command
     * doesn't send a response message.
     * @throws PersistenceException Exception threw when a service problem has happened.
     *                             Can be cause of an invalid or unknown session key.
     *                             Also can be caused of network problems tha waiting time
     *                             expires during the intent to accomplish the remote server.
     */
    @Override
    public SSHCommandRequest<String> executeCommand(SSHCommandRequest<String> command) throws PersistenceException {
        for (SSHSession sshSession : sshSessionList) {
            if (sshSession.getId().equals(command.getSessionKey())) {
                try {
                    command.setResponse(sshSession.executeCommand(command.getCommand()));
                    LogProcessService.getInstance().genericSSHConnectionLog(sshSession.getId(),
                            LogProcessService.getInstance().CONFIRMATION,
                            "Ejecución de comando: " + command.getCommand());
                    return command;
                } catch (Exception e) {
                    LogProcessService.getInstance().genericSSHConnectionLog(sshSession.getId(),
                            LogProcessService.getInstance().ERROR,
                            "Ejecución de comando: " + command.getCommand());
                    throw new PersistenceException("Error when trying to execute SSH Command Details: " + e.getMessage(), BusinessException.SSH_SESSION_COMMAND_EXECUTION_FAILED);
                }

            }
        }
        throw new PersistenceException("SSH Session key not found", BusinessException.INVALID_SSH_TOKEN);
    }
    /**
     * Checks a SSH Session is still up an the server is responding or not.
     * <p>
     *
     * @param sessionKey Session Key to identify the session.
     * @return {@code true} If the session is still up and the server is listening
     * {@code false} if is there isn't an active with the session key provided.
     * @throws PersistenceException Exception threw when a service problem has happened.
     *                             Can be cause of an invalid or unknown session key.
     *                             Also can be caused of network problems tha waiting time
     *                             expires during the intent to accomplish the remote server.
     */
    @Override
    public boolean isSessionActive(String sessionKey) throws PersistenceException {
        for (SSHSession sshSession : sshSessionList) {
            if (sshSession.getId().equals(sessionKey))
                return sshSession.isConnected();
        }
        throw new PersistenceException("SSH Session key not found", BusinessException.INVALID_SSH_TOKEN );
    }
}
