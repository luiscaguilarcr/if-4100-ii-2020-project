package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;

/**
 * @param <E> Type of remote server.
 * @param <K> Session Key type
 * @param <T> SSH Command type.
 */
public interface SshService<E, K, T> {
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
    K startSession(E remoteServer) throws BusinessException, PersistenceException;

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
    boolean endSession(K sessionKey) throws BusinessException, PersistenceException;
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
    boolean endSessions(Integer userId) throws BusinessException, PersistenceException;

    /**
     * Executes a SSH Command.
     * <p>
     * This methods will executes a SSH Session using the Session Key provided to identify
     * the server.
     *
     * @param command    SSH Command to be executed.
     * @return {@code R} Response provided by the Remote Server {@code null} if the command
     * doesn't send a response message.
     * @throws BusinessException Exception threw when a service problem has happened.
     *                             Can be cause of an invalid or unknown session key.
     *                             Also can be caused of network problems tha waiting time
     *                             expires during the intent to accomplish the remote server.
     */
    T executeCommand(T command) throws BusinessException, PersistenceException;

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
    boolean isSessionActive(K sessionKey) throws BusinessException, PersistenceException;

}
