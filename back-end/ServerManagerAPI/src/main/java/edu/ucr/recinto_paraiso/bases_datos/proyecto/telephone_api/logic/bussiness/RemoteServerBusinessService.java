package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.transformation.RemoteServerPersistenceService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.RemoteServerService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.RemoteServer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.SSHConfiguration;

import java.util.List;

/**
 * This services is use con validate, store and request for information related with remote servers.
 * <p>
 * Implements the Singleton Pattern.
 */
public class RemoteServerBusinessService implements RemoteServerService<RemoteServer> {
    /* Instance */
    private static RemoteServerBusinessService instance;
    private final RemoteServerService<RemoteServer> remoteServerPersistenceService;
    /**
     * Constructor of the class. Receives an injection of the remote server persistence service
     * that manages the data of the RemoteServer.
     *
     * @param remoteServerPersistenceService Remote Server persistence Service.
     */
    private RemoteServerBusinessService(RemoteServerService<RemoteServer> remoteServerPersistenceService) {
        this.remoteServerPersistenceService = remoteServerPersistenceService;
    }
    /**
     * Get the instance of the RemoteServer Internal Service.
     *
     * @return {@code UserPersistenceService} instance of the service.
     */
    public static RemoteServerBusinessService getInstance() {
        if (instance == null) {
            instance = new RemoteServerBusinessService(RemoteServerPersistenceService.getInstance());
        }
        return instance;
    }
    /**
     * Search and returns the list of remote servers linked with the user's id provided.
     *
     * @param id ID of the remote server Administrator.
     * @return {@code List<E>} List of remote servers found.
     * @throws BusinessException Exception threw when an RemoteServer-
     *                                      ServiceException occurs while is trying
     *                                      to get the list of servers.
     */
    @Override
    public List<RemoteServer> getByUserId(Integer id) throws BusinessException, PersistenceException {
        if (id == null) {
            throw new BusinessException("The remote server's user ID can't be null.", BusinessException.SERVER_ADMIN_ID_NOT_PROVIDED);
        }
        return remoteServerPersistenceService.getByUserId(id);
    }
    /**
     * Search and returns the remote servers linked with the user's id and server id provided.
     *
     * @param userId ID of the user.
     * @param serverId ID of remote server.
     * @return {@code E} Remote server found.
     * @throws BusinessException Exception threw when an RemoteServer-
     *                                      ServiceException occurs while is trying
     *                                      to get the list of servers.
     */
    @Override
    public RemoteServer getServer(Integer userId, Integer serverId) throws BusinessException, PersistenceException {
        if (userId == null) {
            throw new BusinessException("The remote server's user ID can't be null.", BusinessException.SERVER_ADMIN_ID_NOT_PROVIDED);
        }
        if (serverId == null) {
            throw new BusinessException("The remote server's ID can't be null.", BusinessException.SERVER_ID_NOT_PROVIDED);
        }
        return remoteServerPersistenceService.getServer(userId, serverId);
    }
    /**
     * Inserts a new RemoteServer to the repository. This also validates if the
     * server is valid.
     *
     * @param remoteServer RemoteServer to be added.
     * @return {@code true} if the RemoteServer have been added, {@code false} otherwise.
     * @throws BusinessException Exception threw if an ServiceException
     *                                      occurs while is trying to insert the
     *                                      remote server.
     */
    @Override
    public boolean insert(RemoteServer remoteServer) throws BusinessException, PersistenceException {
        validateRemoteServer(remoteServer);
        return remoteServerPersistenceService.insert(remoteServer);
    }
    /**
     * This updates RemoteServer.
     * Receives the RemoteServer to be modified. This also checks if the update is valid.
     *
     * @param remoteServer RemoteServer to be modified.
     * @return {@code true} if the User have been modified, {@code false} otherwise.
     * @throws BusinessException Exception threw if an ServiceException
     *                                      occurs while is trying to modify the
     *                                      remote server.
     */
    @Override
    public boolean update(RemoteServer remoteServer) throws BusinessException, PersistenceException {
        validateRemoteServer(remoteServer);
        if (remoteServer.getId() == null) {
            throw new BusinessException("The remote server's ID can't be null.", BusinessException.SERVER_ID_NOT_PROVIDED);
        }
        return remoteServerPersistenceService.update(remoteServer);
    }
    /**
     * Deletes a RemoteServer of the repository.
     *
     * @param remoteServer RemoteServer to be deleted.
     * @return {@code true} if the RemoteServer have been added, {@code false} otherwise.
     * @throws BusinessException Exception threw if an ServiceException
     *                                      occurs while is trying to delete the
     *                                      remote server.
     */
    @Override
    public boolean delete(RemoteServer remoteServer) throws BusinessException, PersistenceException {
        if (remoteServer == null) {
            throw new BusinessException("The remote server can't be null.", BusinessException.SERVER_NOT_PROVIDED);
        }
        if (remoteServer.getId() == null) {
            throw new BusinessException("The remote server's ID can't be null.", BusinessException.SERVER_ID_NOT_PROVIDED);
        }
        return remoteServerPersistenceService.delete(remoteServer);
    }
    /**
     * Validate the basics requirements of Remote Server.
     *
     * @param remoteServer Remote server to validate
     * @throws BusinessException Exception threw when one basic requirement
     *                                      is not valid.
     */
    private void validateRemoteServer(RemoteServer remoteServer) throws BusinessException {
        if (remoteServer == null) {
            throw new BusinessException("The remote server can't be null.", BusinessException.SERVER_NOT_PROVIDED);
        }
        if (remoteServer.getAdminId() == null) {
            throw new BusinessException("The remote server's admin ID can't be null.", BusinessException.SERVER_ADMIN_ID_NOT_PROVIDED);
        }
        if (remoteServer.getLabel() == null || remoteServer.getLabel().equals("")) {
            throw new BusinessException("The remote server's label can't be null.", BusinessException.SERVER_LABEL_NOT_PROVIDED);
        }
        if (remoteServer.getOperativeSystem() == null) {
            throw new BusinessException("The remote server's operative system can't be null.", BusinessException.SERVER_OPERATIVE_SYSTEM_NOT_PROVIDED);
        }
        validateSSHConfiguration(remoteServer.getSshConfiguration());
    }
    /**
     * Validates the basic's ssh configuration requirements needed to establish an ssh session.
     *
     * @param sshConfiguration SSH configuration to be validate.
     * @throws BusinessException Exception threw when one basic requirement
     *                                      is not valid.
     */
    private void validateSSHConfiguration(SSHConfiguration sshConfiguration) throws BusinessException {
        if (sshConfiguration == null) {
            throw new BusinessException("The remote server's SSH Configuration can't be null.", BusinessException.SERVER_SSH_CONFIGURATION_NOT_PROVIDED);
        }
        if (sshConfiguration.getServerAddress() == null) {
            throw new BusinessException("The remote server's ip address can't be null.", BusinessException.SERVER_ADDRESS_NOT_PROVIDED);
        }
        if (sshConfiguration.getPort() <= 0 || sshConfiguration.getPort() >= 65535) {
            throw new BusinessException("The remote server's is not a valid port.", BusinessException.INVALID_SERVER_PORT);
        }
        if (sshConfiguration.getSessionTime() < 0) {
            throw new BusinessException("The remote server's SSH session time can't be less or equal than 0.", BusinessException.INVALID_SERVER_SESSION_TIME);
        }
    }
}
