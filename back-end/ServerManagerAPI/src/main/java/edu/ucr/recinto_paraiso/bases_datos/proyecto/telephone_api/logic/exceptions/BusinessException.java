package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions;

/**
 * This class is used to manage error and service exceptions.
 * The code's list can be defined differently in each server. Should add documentation
 * to help other developers to identify the code of the exception.
 * <p>
 * Use null as default or unknown error.
 */
public class BusinessException extends Exception {
    /* User exception codes */
    public static final int USER_OBJECT_NOT_PROVIDED = 51;
    public static final int USER_EMAIL_NOT_PROVIDED = 54;
    public static final int USER_EMAIL_NOT_VALID = 54;
    public static final int USER_NEW_EMAIL_NOT_PROVIDED = 54;
    public static final int USER_NEW_EMAIL_NOT_VALID = 54;
    public static final int USER_EMAIL_IN_USE = 54;
    public static final int USER_CALL_IN_USE = 54;
    public static final int USER_FIRST_NAME_NOT_PROVIDED = 54;
    public static final int USER_LAST_NAME_NOT_PROVIDED = 54;;
    public static final int USER_NEW_FIRST_NAME_NOT_PROVIDED = 54;
    public static final int USER_NEW_LAST_NAME_NOT_PROVIDED = 54;
    public static final int USER_PASSWORD_NOT_PROVIDED = 55;
    public static final int USER_PASSWORD_NOT_SECURE = 55;
    public static final int USER_NEW_PASSWORD_NOT_PROVIDED = 55;
    public static final int USER_NEW_PASSWORD_NOT_SECURE = 55;
    public static final int USER_CREATION_ERROR = 55;

    /* Call exception codes */
    public static final int CALL_OBJECT_NOT_PROVIDED = 1;
    public static final int CALL_TELEPHONE_NUMBER_NOT_PROVIDED = 1;
    public static final int CALL_DESTINATION_TELEPHONE_NUMBER_NOT_PROVIDED = 1;
    public static final int CALL_END_DATE_NOT_PROVIDED = 1;
    public static final int CALL_START_DATE_NOT_PROVIDED = 1;

    /* RemoteServer exception codes */
    public static final int SERVER_NOT_CREATED = 60;
    public static final int SERVER_NOT_UPDATED = 61;
    public static final int SERVER_NOT_DELETED = 62;
    public static final int SERVER_NOT_PROVIDED = 63;
    public static final int SERVER_ID_NOT_PROVIDED = 64;
    public static final int SERVER_LABEL_NOT_PROVIDED = 65;
    public static final int SERVER_OPERATIVE_SYSTEM_NOT_PROVIDED = 66;
    public static final int SERVER_ADMIN_ID_NOT_PROVIDED = 67;
    public static final int SERVER_SSH_CONFIGURATION_NOT_PROVIDED = 68;
    public static final int SERVER_ADDRESS_NOT_PROVIDED = 69;
    public static final int INVALID_SERVER_PORT = 70;
    public static final int INVALID_SERVER_SESSION_TIME = 71;
    public static final int REMOTE_SERVER_USERNAME_NOT_PROVIDED = 73;
    public static final int REMOTE_SERVER_PASSWORD_NOT_PROVIDED = 74;
    public static final int SERVER_ID_NOT_FOUND = 75;

    /* Ssh exception codes */
    public static final int INVALID_SSH_TOKEN = 81;
    public static final int SSH_TOKEN_NOT_PROVIDED = 82;
    public static final int SSH_COMMAND_NOT_PROVIDED = 83;
    public static final int SSH_SESSION_CONNECTION_FAILED = 85;
    public static final int END_SSH_SESSION_CONNECTION_FAILED = 86;
    public static final int SSH_SESSION_COMMAND_EXECUTION_FAILED = 87;

    /* Line */
    public static final int JSON_FORMAT_EXCEPTION = 87;
    public static final int LINE_TELEPHONE_NUMBER_IN_USE = 54;
    public static final int LINE_NOT_DELETED = 82;
    private final Integer code;

    /**
     * Creates a new Service Exception object.
     *
     * @param message Details of the Exception.
     * @param code    Code to classify the Exception.
     *                <p>
     *                Use null as default or unknown error.
     */
    public BusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
