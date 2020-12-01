package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions;

public class PersistenceException extends Exception{
    /* Database exception codes */
    public static final int DATABASE_CONNECTION_FAILED = 55;

    /* code */
    private final int code;
    /**
     * Creates a new Persistence Exception object.
     *
     * @param message Details of the Exception.
     * @param code    Code to classify the Exception.
     *                <p>
     *                Use null as default or unknown error.
     */
    public PersistenceException(String message, Integer code) {
        super(message);
        this.code = code;
    }
    /**
     * Return excetion code
     * @return Integer exception code.
     */
    public Integer getCode() {
        return code;
    }
}
