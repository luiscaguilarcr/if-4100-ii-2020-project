package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.exceptions.SecurityException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;

public interface LoginService<T> {
    /**
     * This method validates the email and password are valid. Creates and returns a new login session
     * with the token session and the expire time. The ip address is used to identify the origin request.
     *
     * @param email    Email of the user.
     * @param password Password of the user.
     * @param IP       Origin IP of the client.
     * @return {@code T} if the data is valid and the token has been generated. {@code null} If the
     * login session was not establish
     * @throws BusinessException Exception threw when required data is not provided or the service
     *                               has connectivity problems.
     */
    T login(String email, String password, String IP) throws BusinessException, PersistenceException, SecurityException;

    /**
     * This method ends an active login session.
     *
     * @param loginSession Login Session object.
     * @return {@code true} if the session have been closed. {@code false} if an error appears while is trying to
     * close the session.
     * @throws BusinessException Exception threw when required data is not provided or the service
     *                               has connectivity problems.
     *                               Also can be caused by an invalid or corrupted data like wrong token or
     *                               different ip.
     */
    boolean logout(T loginSession) throws BusinessException, PersistenceException, SecurityException;

    /**
     * This method validates if the login session is still active and data provided is correct.
     *
     * @param loginSession Login Session object.
     * @return {@code true} if the login session is valid. {@code false} if the session has expired.
     * @throws BusinessException Exception threw when required data is not provided or the service
     *                               has connectivity problems.
     *                               Also can be caused by an invalid or corrupted data like wrong token or
     *                               different ip.
     */
    boolean validateSession(T loginSession) throws BusinessException, PersistenceException, SecurityException;

    /**
     * This method create a new User register.
     *
     * @param email     Email of the user.
     * @param password  Password of the user.
     * @param firstName FirstName of the user.
     * @param lastName  Lastname of the user.
     * @return {@code true} if the register have been done. {@code false} if the session has expired.
     * @throws BusinessException Exception threw when required data is not provided or the service
     *                               has connectivity problems.
     */
    boolean register(String email, String password, String firstName, String lastName) throws BusinessException, PersistenceException, SecurityException;


}
