package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.JsonUtil;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseBuilder;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.ResponseTemplates;
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
            final String body = Utility.getBody(req.getReader());
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
            final String body = Utility.getBody(req.getReader());
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
            try {
                if (LineCustomerBusinessService.getInstance().deleteByTelephoneNumber(new LineCustomerBuilder()
                        .setTelephoneNumber(Integer.parseInt(telephoneNumber))
                        .build())) {
                    okResponse(responseBuilder);
                } else {
                    throw new BusinessException("LineCustomer with telephone number " + telephoneNumber +
                            " haven't been deleted. Verify the telephone number.", BusinessException.LINE_CUSTOMER_NOT_DELETED);
                }
            }catch (NullPointerException exception){
                throw new BusinessException("Line customer telephone number not provided", BusinessException.LINE_CUSTOMER_TELEPHONE_NUMBER_NOT_PROVIDED);
            }
        } catch (BusinessException exception) {
            /* Business Exception */
            ResponseTemplates.businessExceptionResponse(responseBuilder, exception);
        } catch (PersistenceException exception) {
            /* Persistence Exception */
            ResponseTemplates.persistenceExceptionResponse(responseBuilder, exception);
        } finally {
            responseBuilder.setAllowMethods(DELETE);
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
    private static final JsonUtil jsonUtil = new JsonUtil();
    /* LineCustomer Headers */
    static final String telephoneNumber = "telephoneNumber";
    static final String id = "id";
    static final String firstName = "firstName";
    static final String lastName = "lastName";
    static final String address = "address";
    static final String email = "email";
    static String getHeaders(){
        return String.join(",", ProcessLineCustomerRequest.telephoneNumber, ProcessLineCustomerRequest.id, ProcessLineCustomerRequest.firstName, ProcessLineCustomerRequest.lastName, ProcessLineCustomerRequest.address, ProcessLineCustomerRequest.email);
    }

    static LineCustomer createLineCustomer(final String body){
        return jsonUtil.asObject(body, LineCustomer.class);
    }
}
