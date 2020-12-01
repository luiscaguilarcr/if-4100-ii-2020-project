package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.JsonUtil;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LineCustomer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.LineCustomer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.LineCustomerBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.LineCustomerBusinessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.util.Utility;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys.Content_type;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys.getInformation_headers;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HttpMethodsKeys.*;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates.*;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates.okResponse;

public class LineCustomerServlet extends HttpServlet {
    final String headersKeys = String.join(",", getInformation_headers(), ProcessLineCustomerRequest.getHeaders());
    /**
     * Use to insert a new remote server.
     *
     * @param req  request.
     * @param resp response.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        try {
            /* Body */
            final Map<String, String> body = Utility.getBodyMap(req.getReader());
            /* Create LineCustomer */
            final LineCustomer lineCustomer = ProcessLineCustomerRequest.createLineCustomer(body);
            /* Try insert */
            LineCustomerBusinessService.getInstance().insert(lineCustomer);
            /* LineCustomer created */
            resourceCreatedResponse(responseBuilder);
        } catch (IOException ioException) {
            /* JSON FORMAT EXCEPTION */
            jsonFormatErrorResponse(responseBuilder);
        } catch (BusinessException exception) {
            /* Business Exception */
            ResponseTemplates.businessExceptionResponse(responseBuilder, exception);
        } catch (PersistenceException exception) {
            /* Persistence Exception */
            ResponseTemplates.persistenceExceptionResponse(responseBuilder, exception);
        } finally {
            responseBuilder.setAllowMethods(POST);
            responseBuilder.setAllowHeaders(headersKeys);
            responseBuilder.setExposeHeaders(headersKeys);
            responseBuilder.build();
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        try {
            /* Body */
            final Map<String, String> body = Utility.getBodyMap(req.getReader());
            /* Create LineCustomer */
            final LineCustomer lineCustomer = ProcessLineCustomerRequest.createLineCustomer(body);
            /* Try update */
            LineCustomerBusinessService.getInstance().update(lineCustomer);
            okResponse(responseBuilder);
        } catch (IOException ioException) {
            /* JSON FORMAT EXCEPTION */
            jsonFormatErrorResponse(responseBuilder);
        } catch (BusinessException exception) {
            /* Business Exception */
            ResponseTemplates.businessExceptionResponse(responseBuilder, exception);
        } catch (PersistenceException exception) {
            /* Persistence Exception */
            ResponseTemplates.persistenceExceptionResponse(responseBuilder, exception);
        } finally {
            responseBuilder.setAllowMethods(PUT);
            responseBuilder.setAllowHeaders(headersKeys);
            responseBuilder.setExposeHeaders(headersKeys);
            responseBuilder.build();
        }
    }
    @Override
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
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        try {
            /* Headers */
            final String telephoneNumber = req.getHeader(ProcessLineCustomerRequest.telephoneNumber);
            /* try delete */
            if (LineCustomerBusinessService.getInstance().delete(new LineCustomerBuilder()
                    .setTelephone_Number(Integer.parseInt(telephoneNumber))
                    .build())) {
                okResponse(responseBuilder);
            } else {
                throw new BusinessException("LineCustomer with telephone number " + telephoneNumber +
                        " haven't been deleted. Verify the telephone number.", BusinessException.LineCustomer_NOT_DELETED);
            }
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
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
        responseBuilder.setStatus(HttpServletResponse.SC_NO_CONTENT);
        responseBuilder.setAllowMethods(POST, OPTIONS, GET, PUT, DELETE);
        responseBuilder.setAllowHeaders(String.join(",", Content_type, ProcessLineCustomerRequest.getHeaders()));
        responseBuilder.setExposeHeaders(headersKeys);
        responseBuilder.build();
    }
}

class ProcessLineCustomerRequest{
    /* LineCustomer Headers */
    static final String telephoneNumber = "telephoneNumber";
    static final String destinationTelephoneNumber = "destinationTelephoneNumber";
    static final String startDate = "startDate";
    static final String endDate = "endDate";
    static String getHeaders(){
        return String.join(",", ProcessLineCustomerRequest.telephoneNumber, ProcessLineCustomerRequest.destinationTelephoneNumber, ProcessLineCustomerRequest.startDate, ProcessLineCustomerRequest.endDate);
    }

    static LineCustomer createLineCustomer(final Map<String, String> body){
        /* Attributes */
        final int telephoneNumber = Integer.parseInt(body.get(ProcessLineCustomerRequest.telephoneNumber));
        final int destinationTelephone_Number = Integer.parseInt(body.get(ProcessLineCustomerRequest.destinationTelephoneNumber));
        final String startDate = body.get(ProcessLineCustomerRequest.startDate);
        final String endDate = body.get(ProcessLineCustomerRequest.endDate);
        /* Build */
        return new LineCustomerBuilder()
                .setTelephone_Number(telephoneNumber)
                .setDestination_Telephone_Number(destinationTelephone_Number)
                .setStart_Date(startDate)
                .setEnd_Date(endDate)
                .build();
    }
}
