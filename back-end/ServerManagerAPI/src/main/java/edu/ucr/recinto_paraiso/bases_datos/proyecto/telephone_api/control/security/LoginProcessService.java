package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LoginSession;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.RemoteServer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.SSHCommandRequest;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.User;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.UserBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.SshBusinessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.UserBusinessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.LoginService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.UserService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.util.Utility;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.exceptions.SecurityException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.services.interfaces.SshService;

import java.time.LocalDateTime;
import java.util.*;

public class LoginProcessService implements LoginService<LoginSession> {
    /* Instance */
    private static LoginProcessService instance;
    /* Session list */
    private final Map<String, LoginSession> loginSessionList;
    private final List<User> loggedUserList;
    /* Services */
    private final UserService<User> userService;
    private final SshService<RemoteServer, String, SSHCommandRequest<String>> sshService;
    /* Constructor */
    private LoginProcessService(
            final UserService<User> userService,
            final SshService<RemoteServer, String, SSHCommandRequest<String>> sshService
    ) {
        this.loginSessionList = new HashMap<>();
        this.loggedUserList = new ArrayList<>();
        this.userService = userService;
        this.sshService = sshService;
    }
    public static LoginProcessService getInstance() {
        if (instance == null) {
            instance = new LoginProcessService(
                    UserBusinessService.getInstance(),
                    SshBusinessService.getInstance()

            );
        }
        return instance;
    }
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
     *                           has connectivity problems.
     */
    @Override
    public LoginSession login(final String email, final String password, final String IP) throws BusinessException, PersistenceException, SecurityException {
        if (email == null || email.equals("")) {
            throw new SecurityException("User's login email not provided", SecurityException.LOGIN_EMAIL_NOT_PROVIDED);
        }
        if (!Utility.isEmailValid(email)) {
            throw new SecurityException("User's login email not valid.", SecurityException.LOGIN_EMAIL_NOT_VALID);
        }
        if (password == null  || password.equals("")) {
            throw new SecurityException("User's login password not provided", SecurityException.LOGIN_PASSWORD_NOT_PROVIDED);
        }
        if (IP == null) {
            throw new SecurityException("Request ip address not provided", SecurityException.REQUEST_IP_ADDRESS_NOT_PROVIDED);
        }
        /* Get user */
        final User user = userService.get(email);
        /* Validate user and password */
        if (user != null && user.getPassword().equals(password)) {
            int EXPIRE_TIME = 60;
            /* */
            if (loggedUserList.contains(user)) {
                /* User already logged */
                final Object[] list = loginSessionList.values().toArray();
                for (Object obj : list) {
                    final LoginSession loginSession = (LoginSession) obj;
                    if (loginSession.getUserId().equals(user.getId())) {
                        /* EXPIRED -> SET-EXPIRE_TIME AGAIN  */
                        if (loginSession.getExpireDate().isBefore(LocalDateTime.now())) {
                            loginSession.setExpireDate(LocalDateTime.now().plusMinutes(EXPIRE_TIME));
                        }
                        return loginSession;
                    }
                }
            }
            /* Generate token */
            final String uuid = UUID.randomUUID().toString();
            /* Create session object */
            final LoginSession session =
                    new LoginSession(
                            user.getId(),
                            uuid,
                            LocalDateTime.now().plusMinutes(EXPIRE_TIME),
                            IP);
            loginSessionList.put(uuid, session);
            loggedUserList.add(user);
            return session;
        } else { /* Invalid email or password */
            throw new SecurityException("Email or password not valid.", SecurityException.LOGIN_AUTHENTICATION_NOT_VALID);
        }
    }
    /**
     * This method ends an active login session.
     *
     * @param loginSession Login Session object.
     * @return {@code true} if the session have been closed. {@code false} if an error appears while is trying to
     * close the session.
     * @throws BusinessException Exception threw when required data is not provided or the service
     *                           has connectivity problems.
     *                           Also can be caused by an invalid or corrupted data like wrong token or
     *                           different ip.
     */
    @Override
    public boolean logout(LoginSession loginSession) throws BusinessException, SecurityException, PersistenceException {
        if (loginSession == null) {
            throw new SecurityException("Login session object not provided.", SecurityException.LOGIN_SESSION_NOT_PROVIDED);
        }
        if (validateSession(loginSession)) {
            for (User user : loggedUserList) {
                if (user.getId().equals(loginSession.getUserId())) {
                    loginSessionList.remove(loginSession.getSessionToken());
                    return loggedUserList.remove(user) &&
                            sshService.endSessions(user.getId());
                }
            }
        }
        return false;
    }
    /**
     * This method validates if the login session is still active and data provided is correct.
     *
     * @param loginSession Login Session object.
     * @return {@code true} if the login session is valid. {@code false} if the session has expired.
     * @throws SecurityException Exception threw when required data is not provided or the service
     *                           has connectivity problems.
     *                           Also can be caused by an invalid or corrupted data like wrong token or
     *                           different ip.
     */
    @Override
    public boolean validateSession(LoginSession loginSession) throws SecurityException {
        if (loginSession == null) {
            throw new SecurityException("Login session object not provided.", SecurityException.LOGIN_SESSION_NOT_PROVIDED);
        }
        if (loginSession.getIP() == null) {
            throw new SecurityException("Request ip address not provided", SecurityException.REQUEST_IP_ADDRESS_NOT_PROVIDED);
        }
        if (loginSession.getUserId() == null) {
            throw new SecurityException("User's login id not provided.", SecurityException.LOGIN_USER_ID_NOT_PROVIDED);
        }
        final LoginSession session = loginSessionList.get(loginSession.getSessionToken());
        if (session != null && session.equals(loginSession)) {
            return session.getExpireDate().isAfter(LocalDateTime.now());
        }
        return false;
    }
    /**
     * This method create a new User register.
     *
     * @param email     Email of the user.
     * @param password  Password of the user.
     * @param firstName FirstName of the user.
     * @param lastName  Lastname of the user.
     * @return {@code true} if the register have been done. {@code false} if the session has expired.
     * @throws BusinessException Exception threw when required data is not provided or the service
     *                           has connectivity problems.
     */
    @Override
    public boolean register(String email, String password, String firstName, String lastName) throws BusinessException, PersistenceException {
        return userService.insert(new UserBuilder()
                .setEmail(email)
                .setPassword(password)
                .setFirstName(firstName)
                .setLastName(lastName)
                .build());
    }
}
