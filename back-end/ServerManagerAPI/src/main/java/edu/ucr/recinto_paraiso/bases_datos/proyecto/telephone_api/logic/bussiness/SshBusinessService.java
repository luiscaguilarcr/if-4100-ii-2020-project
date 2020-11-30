package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.RemoteServer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.SSHConfiguration;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.SSHCommandRequest;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.SshService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.transformation.SshPersistenceService;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException.*;

/**
 * Service used to manage the SSH session in the server.
 */
public class SshBusinessService implements SshService<RemoteServer, String, SSHCommandRequest<String>> {
    /* Instance */
    private static SshBusinessService instance;
    private final SshService<RemoteServer, String, SSHCommandRequest<String>> persistenceService;
    /**
     * @param persistenceService SshService persistence to store the active session.
     */
    private SshBusinessService(SshService<RemoteServer, String, SSHCommandRequest<String>> persistenceService) {
        this.persistenceService = persistenceService;
    }
    /**
     * Instance class
     * @return SshBusinessService
     */
    public static SshBusinessService getInstance() {
        if (instance == null) {
            instance = new SshBusinessService(SshPersistenceService.getInstance());
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
     * @throws BusinessException Exception threw when a service problem has happened.
     *                             Can be cause of an invalid Server object or SSH
     *                             Configuration provided.
     *                             Also can be caused of network problems tha waiting time
     *                             expires during the intent to accomplish the remote server.
     */
    @Override
    public String startSession(RemoteServer remoteServer) throws BusinessException, PersistenceException {
        validateRemoteServer(remoteServer);
        return persistenceService.startSession(remoteServer);
    }
    /**
     * Ends an active SSH Session.
     * <p>
     * This methods will end the SSH Session that identify by the Session Key provided.
     *
     * @param sessionKey Session Key to identify the session.
     * @return {@code true} if the session has been close successfully {@code false} if
     * an error happened and the session is still open.
     * @throws BusinessException Exception threw when a service problem has happened.
     *                             Can be cause of an invalid or unknown session key.
     *                             Also can be caused of network problems tha waiting time
     *                             expires during the intent to accomplish the remote server.
     */
    @Override
    public boolean endSession(String sessionKey) throws BusinessException, PersistenceException {
        if (sessionKey == null) {
            throw new BusinessException("Invalid session key. Details: The key can't be null.", SSH_TOKEN_NOT_PROVIDED);
        }
        return persistenceService.endSession(sessionKey);
    }
    /**
     * Ends the active SSH Sessions of one user.
     * <p>
     * This methods will end the SSH Session that identify by the Session Key provided.
     *
     * @param userId User id identify the all the sessions related within.
     * @return {@code true} if the session has been close successfully {@code false} if
     * an error happened and the session is still open.
     * @throws BusinessException Exception threw when a service problem has happened.
     *                             Can be cause of an invalid or unknown session key.
     *                             Also can be caused of network problems tha waiting time
     *                             expires during the intent to accomplish the remote server.
     */
    @Override
    public boolean endSessions(Integer userId) throws BusinessException, PersistenceException {
        if (userId == null) {
            throw new BusinessException("Invalid session key. Details: The userID can't be null.", SERVER_ADMIN_ID_NOT_PROVIDED);
        }
        return persistenceService.endSessions(userId);
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
     * @throws BusinessException Exception threw when a service problem has happened.
     *                             Can be cause of an invalid or unknown session key.
     *                             Also can be caused of network problems tha waiting time
     *                             expires during the intent to accomplish the remote server.
     */
    @Override
    public SSHCommandRequest<String> executeCommand(SSHCommandRequest<String> command) throws BusinessException, PersistenceException{
        if (command == null) {
            throw new BusinessException("The SSH Command provided can't be null", SSH_COMMAND_NOT_PROVIDED);
        }
        if (command.getSessionKey() == null) {
            throw new BusinessException("The SSH Session ID can't be null", SSH_TOKEN_NOT_PROVIDED);
        }

        if (command.getCommand() == null || command.getCommand().equals("")) {
            throw new BusinessException("The SSH Command can't be null", SSH_COMMAND_NOT_PROVIDED);
        }
        return persistenceService.executeCommand(command);
    }
    /**
     * Checks a SSH Session is still up an the server is responding or not.
     * <p>
     *
     * @param sessionKey Session Key to identify the session.
     * @return {@code true} If the session is still up and the server is listening
     * {@code false} if is there isn't an active with the session key provided.
     * @throws BusinessException Exception threw when a service problem has happened.
     *                             Can be cause of an invalid or unknown session key.
     *                             Also can be caused of network problems tha waiting time
     *                             expires during the intent to accomplish the remote server.
     */
    @Override
    public boolean isSessionActive(String sessionKey) throws BusinessException, PersistenceException {
        if (sessionKey == null) {
            throw new BusinessException("Invalid session key. Details: The key can't be null.", SSH_TOKEN_NOT_PROVIDED);
        }
        return persistenceService.isSessionActive(sessionKey);
    }
    /**
     * Validate the basics requirements of Remote Server.
     *
     * @param remoteServer Remote server to validate
     * @throws BusinessException Exception threw when one basic requirement
     *                             is not valid.
     */
    private void validateRemoteServer(RemoteServer remoteServer) throws BusinessException {
        if (remoteServer == null) {
            throw new BusinessException("The remote server can't be null.", SERVER_NOT_PROVIDED);
        }
        if (remoteServer.getAdminId() == null) {
            throw new BusinessException("The remote server's admin ID can't be null.", SERVER_ADMIN_ID_NOT_PROVIDED);
        }
        if (remoteServer.getOperativeSystem() == null) {
            throw new BusinessException("The remote server's operative system can't be null.", SERVER_OPERATIVE_SYSTEM_NOT_PROVIDED);
        }
        validateSSHConfiguration(remoteServer.getSshConfiguration());
    }
    /**
     * Validates the basic's ssh configuration requirements needed to establish an ssh session.
     *
     * @param sshConfiguration SSH configuration to be validate.
     * @throws BusinessException Exception threw when one basic requirement
     *                             is not valid.
     */
    private void validateSSHConfiguration(SSHConfiguration sshConfiguration) throws BusinessException {
        if (sshConfiguration == null) {
            throw new BusinessException("The remote server's SSH Configuration can't be null.", SERVER_SSH_CONFIGURATION_NOT_PROVIDED);
        }
        if (sshConfiguration.getServerAddress() == null) {
            throw new BusinessException("The remote server's ip address can't be null.", SERVER_ADDRESS_NOT_PROVIDED);
        }
        if (sshConfiguration.getUsername() == null) {
            throw new BusinessException("The remote server's username can't be null.", REMOTE_SERVER_USERNAME_NOT_PROVIDED);
        }
        if (sshConfiguration.getPassword() == null) {
            throw new BusinessException("The remote server's ip password can't be null.", REMOTE_SERVER_PASSWORD_NOT_PROVIDED);
        }
        if (sshConfiguration.getPort() <= 0 || sshConfiguration.getPort() >= 65535) {
            throw new BusinessException("The remote server's is not a valid port.", INVALID_SERVER_PORT);
        }
        if (sshConfiguration.getSessionTime() < 0) {
            throw new BusinessException("The remote server's SSH session time can't be less than 0.", INVALID_SERVER_SESSION_TIME);
        }
    }
}
