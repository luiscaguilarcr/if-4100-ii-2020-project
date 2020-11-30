package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseBuilder {
    private final HttpServletResponse resp;
    private Integer status;
    private String valid_authorization;
    private String errorMessage;
    private String allowMethods = "POST, GET, OPTIONS, PUT, DELETE";
    private String allowHeaders = "*, Authorization";
    private final String allowOrigin = "*";
    private String exposeHeaders = "*, Authorization";
    private final String requestHeaders = "Content-type";
    private final String contentType = "application/json";
    private String completed;
    private String body;

    private int errorCode;

    public ResponseBuilder(HttpServletResponse resp) {
        this.resp = resp;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setValid_authorization(boolean valid_authorization) {
        this.valid_authorization = valid_authorization?"true":"false";
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public void setAllowMethods(String...allowMethods) {
        this.allowMethods = String.join(",", allowMethods);
    }

    public void setAllowHeaders(String allowHeaders) {
        this.allowHeaders = allowHeaders;
    }

    public void setExposeHeaders(String exposeHeaders) {
        this.exposeHeaders = exposeHeaders;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed?"true":"false";
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setHeader(String key, String value){
        resp.setHeader(key, value);
    }
    public void setIntHeader(String key, Integer value){
        resp.setIntHeader(key, value);
    }
    public void build() {
        resp.setStatus(status);
        resp.setHeader("valid_authorization", valid_authorization);
        resp.setHeader("completed", completed);
        resp.setHeader("error_message", errorMessage);
        resp.setIntHeader("error_code", errorCode);
        /* CORS headers */
        resp.setHeader("Content-type", contentType);
        resp.setHeader("Access-Control-Allow-Origin", allowOrigin);
        resp.setHeader("Access-Control-Allow-Methods", allowMethods);
        resp.setHeader("Access-Control-Allow-Headers",  allowHeaders);
        resp.setHeader("Access-Control-Expose-Headers", exposeHeaders);
        resp.setHeader("Access-Control-Request-Headers", requestHeaders);
        resp.setIntHeader("Access-Control-Max-Age", 86400);
        if(body !=null){
            try {
                resp.getWriter().println(body);
                resp.getWriter().flush();
            } catch (IOException e){
                System.err.println("Writer exception found" + e.getLocalizedMessage());
            }
        }
    }
}
