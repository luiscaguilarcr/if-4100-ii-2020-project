package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions;

/**
 * This class is used to manage error and service exceptions.
 * The code's list can be defined differently in each server. Should add documentation
 * to help other developers to identify the code of the exception.
 * <p>
 * Use null as default or unknown error.
 */
public class BusinessException extends Exception {
    /* Call service exception codes */
    public static final int CALL_OBJECT_NOT_PROVIDED = 1;
    public static final int CALL_NUMBER_IN_USE = 1;
    public static final int CALL_TELEPHONE_NUMBER_NOT_PROVIDED = 1;
    public static final int CALL_DESTINATION_TELEPHONE_NUMBER_NOT_PROVIDED = 1;
    public static final int CALL_END_DATE_NOT_PROVIDED = 1;
    public static final int CALL_START_DATE_NOT_PROVIDED = 1;

    /* Service service exception codes */
    public static final int SERVICE_CODE_IN_USE = 1;
    public static final int SERVICE_CODE_NOT_VALID = 1;
    public static final int SERVICE_CODE_NOT_DELETED = 82;
    public static final int SERVICE_COST_NOT_PROVIDED = 82;

    /* CallServlet exception codes */
    public static final int CALL_NOT_DELETED = 1;
    /* Json format */
    public static final int JSON_FORMAT_EXCEPTION = 87;
    /* Line */
    public static final int LINE_TELEPHONE_NUMBER_IN_USE = 54;
    public static final int LINE_NOT_DELETED = 82;
    public static final int LINE_TELEPHONE_NUMBER_NOT_VALID = 82;
    public static final int LINE_POINT_QUANTITY_NOT_PROVIDED = 82;
    public static final int LINE_STATUS_NOT_VALID = 82;
    public static final int LINE_TYPE_NOT_PROVIDED = 82;
    /* Line */
    public static final int LINE_CUSTOMER_TELEPHONE_NUMBER_IN_USE = 54;
    public static final int LINE_CUSTOMER_NOT_DELETED = 82;
    public static final int LINE_CUSTOMER_TELEPHONE_NUMBER_NOT_VALID = 82;
    public static final int LINE_CUSTOMER_TELEPHONE_NUMBER_NOT_PROVIDED = 82;
    public static final int LINE_CUSTOMER_ID_NOT_PROVIDED = 82;
    public static final int LINE_CUSTOMER_FIRST_NAME_NOT_PROVIDED = 82;
    public static final int LINE_CUSTOMER_LAST_NAME_NOT_PROVIDED = 82;
    public static final int LINE_CUSTOMER_EMAIL_NOT_PROVIDED = 82;
    public static final int LINE_CUSTOMER_EMAIL_NOT_VALID = 82;
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
