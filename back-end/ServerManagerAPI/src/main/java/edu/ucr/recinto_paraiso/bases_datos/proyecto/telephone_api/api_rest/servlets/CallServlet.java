package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.JsonUtil;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Call;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.CallBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.CallBusinessService;
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

public class CallServlet extends HttpServlet {
    final String headersKeys = String.join(",", getInformation_headers(), ProcessLineRequest.getHeaders());
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
            /* Create Call */
            final Call call = ProcessCallRequest.createCall(body);
            /* Try insert */
            CallBusinessService.getInstance().insert(call);
            /* Call created */
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
            /* Create Call */
            final Call call = ProcessCallRequest.createCall(body);
            /* Try update */
            CallBusinessService.getInstance().update(call);
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
            List<Call> list = CallBusinessService.getInstance().get();
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
            final String telephoneNumber = req.getHeader(ProcessCallRequest.telephoneNumber);
            /* try delete */

            if (CallBusinessService.getInstance().delete(new CallBuilder()
                    .setTelephone_Number(Integer.parseInt(telephoneNumber))
                    .build())) {
                okResponse(responseBuilder);
            } else {
                throw new BusinessException("Call with telephone number " + telephoneNumber +
                        " haven't been deleted. Verify the telephone number.", BusinessException.CALL_NOT_DELETED);
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
        responseBuilder.setAllowHeaders(String.join(",", Content_type, ProcessCallRequest.getHeaders()));
        responseBuilder.setExposeHeaders(headersKeys);
        responseBuilder.build();
    }
}

class ProcessCallRequest{
    /* Call Headers */
    static final String telephoneNumber = "telephoneNumber";
    static final String destinationTelephoneNumber = "destinationTelephoneNumber";
    static final String startDate = "startDate";
    static final String endDate = "endDate";
    static String getHeaders(){
        return String.join(",", ProcessCallRequest.telephoneNumber, ProcessCallRequest.destinationTelephoneNumber, ProcessCallRequest.startDate, ProcessCallRequest.endDate);
    }

    static Call createCall(final Map<String, String> body){
        /* Attributes */
        final int telephoneNumber = Integer.parseInt(body.get(ProcessCallRequest.telephoneNumber));
        final int destinationTelephoneNumber = Integer.parseInt(body.get(ProcessCallRequest.destinationTelephoneNumber));
        final String startDate = body.get(ProcessCallRequest.startDate);
        final String endDate = body.get(ProcessCallRequest.endDate);
        /* Build */
        return new CallBuilder()
                .setTelephone_Number(telephoneNumber)
                .setDestination_Telephone_Number(destinationTelephoneNumber)
                .setStart_Date(startDate)
                .setEnd_Date(endDate)
                .build();
    }
}
