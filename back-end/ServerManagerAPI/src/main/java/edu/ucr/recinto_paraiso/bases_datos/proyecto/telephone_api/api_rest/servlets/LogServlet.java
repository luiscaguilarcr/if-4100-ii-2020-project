package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.exceptions.SecurityException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.LogProcessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.control.security.SessionVerification;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Log;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HttpMethodsKeys;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.JSONUtil;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates.*;

/**
 * URI end-point: /user/logs/
 */
public class LogServlet extends HttpServlet {
    final String headersKeys = String.join(",", HeadersKeys.getInformation_headers(), HeadersKeys.session_token, HeadersKeys.user_id);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        try {
            /* Session verification */
            SessionVerification.getInstance().verifySessionToken(req);
            List<Log> list = LogProcessService.getInstance().getLogs();
            if (list == null) {
                list = new ArrayList<>();
            }
            JSONUtil jsonUtil = new JSONUtil();
            List<String> json = new ArrayList<>();
            for (final Log i : list) {
                json.add(jsonUtil.asJson(i));
            }
            responseBuilder.setBody("[" + String.join(",", json) + "]");
            okResponse(responseBuilder);
        } catch (SecurityException exception) {
            /* Security Exception */
            ResponseTemplates.securityExceptionResponse(responseBuilder, exception);
        } finally {
            responseBuilder.setAllowMethods(HttpMethodsKeys.GET);
            responseBuilder.setAllowHeaders(headersKeys);
            responseBuilder.setExposeHeaders(headersKeys);
            responseBuilder.build();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        responseBuilder.setStatus(HttpServletResponse.SC_NO_CONTENT);
        responseBuilder.setAllowMethods(HttpMethodsKeys.GET, HttpMethodsKeys.OPTIONS);
        responseBuilder.setAllowHeaders(String.join(",", HeadersKeys.Content_type, HeadersKeys.session_token, HeadersKeys.user_id));
        responseBuilder.setExposeHeaders(headersKeys);
        responseBuilder.build();
    }
}
