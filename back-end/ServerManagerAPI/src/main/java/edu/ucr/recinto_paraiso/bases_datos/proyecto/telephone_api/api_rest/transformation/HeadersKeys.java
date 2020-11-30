package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation;

public class HeadersKeys {
    /* Default headers */
    public static final String Content_type = "Content-type";
    public static final String Authorization = "Authorization";
    public static final String valid_authorization = "valid_authorization";
    public static final String completed = "completed";
    public static final String error_message = "error_message";
    public static final String error_code = "error_code";

    public static String getInformation_headers(){
        return String.join(",", Authorization, Content_type, valid_authorization, completed, error_message, error_code);
    }
}
