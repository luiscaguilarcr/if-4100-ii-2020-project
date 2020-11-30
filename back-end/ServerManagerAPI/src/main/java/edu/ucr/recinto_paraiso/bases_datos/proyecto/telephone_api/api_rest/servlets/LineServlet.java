package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.JsonUtil;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.Line;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.domain.builders.LineBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.LineBusinessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.exceptions.BusinessException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.persistence.exceptions.PersistenceException;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.util.Utility;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys.*;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HttpMethodsKeys.*;
import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates.*;

/**
 * URI end-point: /line/
 */
public class LineServlet extends HttpServlet {
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
            final Line line = ProcessLineRequest.createLine(body);
            /* Try insert */
            LineBusinessService.getInstance().insert(line);
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
            final Line line = ProcessLineRequest.createLine(body);
            /* Try update */
            LineBusinessService.getInstance().update(line);
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
            List<Line> list = LineBusinessService.getInstance().get();
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
            final String telephoneNumber = req.getHeader(ProcessLineRequest.telephoneNumber);
            /* try delete */
            if (LineBusinessService.getInstance().delete(new LineBuilder()
                    .setTelephone_Number(Integer.parseInt(telephoneNumber))
                    .build())) {
                okResponse(responseBuilder);
            } else {
                throw new BusinessException("Line with telephone number " + telephoneNumber +
                        " haven't been deleted. Verify the telephone number.", BusinessException.LINE_NOT_DELETED);
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

class ProcessLineRequest{
    /* Line Headers */
    static final String telephoneNumber = "telephoneNumber";
    static final String pointsQuantity = "pointsQuantity";
    static final String type = "type";
    static final String status = "status";
    static String getHeaders(){
        return String.join(",", ProcessLineRequest.telephoneNumber, ProcessLineRequest.pointsQuantity, ProcessLineRequest.type, ProcessLineRequest.status);
    }

    static Line createLine(final Map<String, String> body){
        /* Attributes */
        final int telephoneNumber = Integer.parseInt(body.get(ProcessLineRequest.telephoneNumber));
        final int pointsQuantity = Integer.parseInt(body.get(ProcessLineRequest.pointsQuantity));
        final int type = Integer.parseInt(body.get(ProcessLineRequest.type));
        final String status = body.get(ProcessLineRequest.status);
        /* Build */
        return new LineBuilder()
                .setTelephone_Number(telephoneNumber)
                .setPoints_Quantity(pointsQuantity)
                .setType(type)
                .setStatus(status)
                .build();
    }
}