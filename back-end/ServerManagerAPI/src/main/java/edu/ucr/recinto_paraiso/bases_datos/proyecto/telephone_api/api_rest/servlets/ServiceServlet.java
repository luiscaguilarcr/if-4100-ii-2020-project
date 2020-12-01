package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.JsonUtil;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Line;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Service;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.LineBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.ServiceBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.LineBusinessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.ServiceBusinessService;
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

/**
 * URI end-point: /service/
 */
public class ServiceServlet extends HttpServlet {
    final String headersKeys = String.join(",", getInformation_headers());
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
            /* Create line */
            final Service service = ProcessServiceRequest.createService(body);
            /* Try insert */
            ServiceBusinessService.getInstance().insert(service);
            /* Line created */
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
            /* Create line */
            final Service service = ProcessServiceRequest.createService(body);
            /* Try update */
            ServiceBusinessService.getInstance().update(service);
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
            List<Service> list = ServiceBusinessService.getInstance().get();
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
            final String serviceCode = req.getHeader(ProcessServiceRequest.serviceCode);
            /* try delete */
            if (ServiceBusinessService.getInstance().delete(new ServiceBuilder()
                    .setService_Code(Integer.parseInt(serviceCode))
                    .build())) {
                okResponse(responseBuilder);
            } else {
                throw new BusinessException("Service with code " + serviceCode +
                        " haven't been deleted. Verify the service code.", BusinessException.SERVICE_CODE_NOT_DELETED);
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
        responseBuilder.setAllowHeaders(String.join(",", Content_type, ProcessLineRequest.getHeaders()));
        responseBuilder.setExposeHeaders(headersKeys);
        responseBuilder.build();
    }
}

class ProcessServiceRequest{
    /* Line Headers */
    static final  String serviceCode= "serviceCode" ;
    static final  String name = "name" ;
    static final  String description = "description" ;
    static final  String cost = "cost" ;
    static final  String status = "status" ;


    static String getHeaders(){
        return String.join(",", ProcessServiceRequest.serviceCode,ProcessServiceRequest.name,ProcessServiceRequest.description,
                ProcessServiceRequest.cost,ProcessServiceRequest.status);
    }

    static Service createService(final Map<String, String> body){
        /* Attributes */

        final  int serviceCode = Integer.parseInt(body.get(ProcessServiceRequest.serviceCode));
        final  String name = body.get(ProcessServiceRequest.name);
        final  String description= body.get(ProcessServiceRequest.description);
        final  int cost  = Integer.parseInt(body.get(ProcessServiceRequest.cost)) ;
        final  String status = body.get(ProcessServiceRequest.status);



        /* Build */
        return new ServiceBuilder()
                .setService_Code(serviceCode)
                .setName(name)
                .setDescription(description)
                .setCost(cost)
                .setStatus(status)
                .build();
    }
}