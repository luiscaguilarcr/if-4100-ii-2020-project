package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.JsonUtil;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Call;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Line;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LineCallServiceCustomer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LineCustomer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.CallBusinessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.LineCustomerBusinessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys.getInformation_headers;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HttpMethodsKeys.GET;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates.okResponse;

public class LineCallServiceCustomerServlet extends HttpServlet {
    final String headersKeys = String.join(",", getInformation_headers(), ProcessLineCallServiceCustomerRequest.getHeaders());

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        try {
            JsonUtil jsonUtil = new JsonUtil();
            /* Get list */
            List<LineCustomer> list = LineCustomerBusinessService.getInstance().get();
            /* Parse response */
            responseBuilder.setBody(jsonUtil.asJson(list));
            okResponse(responseBuilder);
        } catch (BusinessException exception) {
            /* Business Exception */
            ResponseTemplates.businessExceptionResponse(responseBuilder, exception);
        } catch (PersistenceException exception) {
            /* Persistence Exception */
            ResponseTemplates.persistenceExceptionResponse(responseBuilder, exception);
        } finally {
            responseBuilder.setAllowMethods(GET);
            responseBuilder.setAllowHeaders(headersKeys);
            responseBuilder.setExposeHeaders(headersKeys);
            responseBuilder.build();
        }
    }
}

class ProcessLineCallServiceCustomerRequest{
    private static final JsonUtil jsonUtil = new JsonUtil();
    /* Line Headers */
    private final String telephoneNumber = "telephoneNumber";
    private final String customerId = "customerId";
    private final String customerFirstName = "customerFirstName";
    private final String customerLastName = "customerLastName";
    private final String status = "status";
    private final String name = "name";
    static String getHeaders(){
        return String.join(",", ProcessLineRequest.telephoneNumber, ProcessLineRequest.pointsQuantity, ProcessLineRequest.type, ProcessLineRequest.status);
    }

    static Line createLine(final String body){
        return jsonUtil.asObject(body, Line.class);
    }
}
