package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation;

public class HeadersKeys {
    public static final String api_token = "api_token";
    public static final String client_token = "client_token";
    public static final String session_token = "session_token";
    public static final String ssh_token = "ssh_token";
    public static final String email = "email";
    public static final String new_email = "new_email";
    public static final String password = "password";
    public static final String new_password = "new_password";
    public static final String first_name = "first_name";
    public static final String last_name = "last_name";
    public static final String user_id = "user_id";
    public static final String user = "user";
    public static final String expire_time = "expire_time";
    /* Default headers */
    public static final String Content_type = "Content-type";
    public static final String Authorization = "Authorization";
    public static final String valid_authorization = "valid_authorization";
    public static final String completed = "completed";
    public static final String error_message = "error_message";
    public static final String error_code = "error_code";
    /* Remote Server */
    public static final String server_id = "server_id";
    public static final String admin_id = "admin_id";
    public static final String label = "label";
    public static final String description = "description";
    public static final String operative_system = "operative_system";
    public static final String version = "version";
    public static final String server_address = "server_address";
    public static final String username = "username";
    public static final String port = "port";
    public static final String session_time = "session_time";
    public static final String command = "command";
    public static final String command_response = "command_response";
    public static final String command_response_type = "command_response_type";


    public static String getInformation_headers(){
        return String.join(",", Authorization, Content_type, valid_authorization, completed, error_message, error_code);
    }
public static String getRemoteServerHeaders(){
        return String.join(",", server_id, admin_id, label, description, operative_system, version, server_address, username, password, port, session_time);
}
}
