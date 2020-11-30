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

    /* Call exception codes */
    public static final int CALL_OBJECT_NOT_PROVIDED = 1;
    public static final int CALL_TELEPHONE_NUMBER_NOT_PROVIDED = 1;
    public static final int CALL_DESTINATION_TELEPHONE_NUMBER_NOT_PROVIDED = 1;
    public static final int CALL_END_DATE_NOT_PROVIDED = 1;
    public static final int CALL_START_DATE_NOT_PROVIDED = 1;

    /* CallServlet exception codes */
    public static final int CALL_NOT_DELETED = 1;

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
