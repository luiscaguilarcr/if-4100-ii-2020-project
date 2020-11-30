package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.RemoteServer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;

import java.util.List;

/**
 * Interface used to manage the remote servers.
 *
 * @param <E> remote server Element.
 */
public interface RemoteServerService<E> {
    /**
     * Search and returns the list of remote servers linked with the user's id provided.
     *
     * @param id ID of the remote server Administrator.
     * @return {@code List<E>} List of remote servers found.
     * @throws PersistenceException Exception threw when an RemoteServer-
     *                                      ServiceException occurs while is trying
     *                                      to get the list of servers.
     */
    List<E> getByUserId(Integer id) throws BusinessException, PersistenceException;

    /**
     * Search and returns the remote servers linked with the user's id and server id provided.
     *
     * @param userId ID of the user.
     * @param serverId ID of remote server.
     * @return {@code E} Remote server found.
     * @throws PersistenceException Exception threw when an RemoteServer-
     *                                      ServiceException occurs while is trying
     *                                      to get the list of servers.
     */
    RemoteServer getServer(Integer userId, Integer serverId) throws BusinessException, PersistenceException;

    /**
     * Inserts a new RemoteServer to the repository. This also validates if the
     * server is valid.
     *
     * @param remoteServer RemoteServer to be added.
     * @return {@code true} if the RemoteServer have been added, {@code false} otherwise.
     * @throws PersistenceException Exception threw if an ServiceException
     *                                      occurs while is trying to insert the
     *                                      remote server.
     */
    boolean insert(E remoteServer) throws BusinessException, PersistenceException;

    /**
     * This updates RemoteServer.
     * Receives the RemoteServer to be modified. This also checks if the update is valid.
     *
     * @param remoteServer RemoteServer to be modified.
     * @return {@code true} if the User have been modified, {@code false} otherwise.
     * @throws PersistenceException Exception threw if an ServiceException
     *                                      occurs while is trying to modify the
     *                                      remote server.
     */
    boolean update(E remoteServer) throws BusinessException, PersistenceException;

    /**
     * Deletes a RemoteServer of the repository.
     *
     * @param remoteServer RemoteServer to be deleted.
     * @return {@code true} if the RemoteServer have been added, {@code false} otherwise.
     * @throws PersistenceException Exception threw if an ServiceException
     *                                      occurs while is trying to delete the
     *                                      remote server.
     */
    boolean delete(E remoteServer) throws BusinessException, PersistenceException;

}
