package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.exceptions;

public class SecurityException extends Exception {
    /* Exception codes */
    public static final int API_TOKEN_NOT_PROVIDED = 0;
    public static final int API_TOKEN_NOT_VALID = 0;
    public static final int CLIENT_TOKEN_NOT_PROVIDED = 0;
    public static final int CLIENT_TOKEN_NOT_VALID = 0;
    public static final int SESSION_TOKEN_NOT_PROVIDED = 0;
    public static final int SESSION_TOKEN_NOT_VALID = 0;
    public static final int SSH_TOKEN_NOT_PROVIDED = 0;
    public static final int SSH_TOKEN_NOT_VALID = 0;
    public static final int LOGIN_USER_ID_NOT_PROVIDED = 0;
    public static final int LOGIN_EMAIL_NOT_PROVIDED = 0;
    public static final int LOGIN_EMAIL_NOT_VALID = 0;
    public static final int LOGIN_PASSWORD_NOT_PROVIDED = 0;
    public static final int LOGIN_AUTHENTICATION_NOT_VALID = 0;
    public static final int LOGIN_SESSION_NOT_PROVIDED = 0;
    public static final int REQUEST_IP_ADDRESS_NOT_PROVIDED = 0;
    public static final int LOGOUT_ERROR = 0;
    public static final int USER_PASSWORD_NOT_VALID = 55;
    private final int code;


    /**
     * Creates a Security Exception.
     *
     * @param detail  Detailed message of the Exception.
     * @param code    Code of the Exception.
     */
    public SecurityException(String detail, Integer code) {
        super(detail);
        this.code = code;
    }

    /**
     * Returns the code number
     * @return {@code int} code of the exception.
     */
    public int getCode() {
        return code;
    }
}
