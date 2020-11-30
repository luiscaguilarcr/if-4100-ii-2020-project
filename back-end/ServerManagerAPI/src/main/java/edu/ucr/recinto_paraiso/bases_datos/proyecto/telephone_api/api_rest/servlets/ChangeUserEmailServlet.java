package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.transformation.HeadersKeys.*;

/**
 * URI end-point: /user/email
 */
public class ChangeUserEmailServlet extends HttpServlet {
    final String headersKeys = String.join(",", getInformation_headers());

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
//        final ResponseBuilder responseBuilder = new ResponseBuilder(resp);
//        try {
//            /* Session verification */
//            final LoginSession loginSession = SessionVerification.getInstance().verifySessionToken(req);
//            /* Body */
//            final Map<String, String> body = Utility.getBodyMap(req.getReader());
//            final String EMAIL = body.get(email);
//            final String PASSWORD = body.get(password);
//            final String NEW_EMAIL = body.get("new_email");
//
//            if (UserBusinessService.getInstance().changeEmail(new UserBuilder()
//                    .setId(loginSession.getUserId())
//                    .setEmail(EMAIL)
//                    .setPassword(PASSWORD)
//                    .setFirstName("")
//                    .setLastName("")
//                    .build(), NEW_EMAIL)) {
//                resp.setStatus(HttpServletResponse.SC_OK);
//                resp.setHeader("valid_authorization", "true");
//            } else {
//                throw new BusinessException("Email not changed.", 0);
//            }
//        } catch (IOException ioException) {
//            /* JSON FORMAT EXCEPTION */
//            jsonFormatErrorResponse(responseBuilder);
//        } catch (SecurityException exception) {
//            /* Security Exception */
//            ResponseTemplates.securityExceptionResponse(responseBuilder, exception);
//        } catch (BusinessException exception) {
//            /* Business Exception */
//            ResponseTemplates.businessExceptionResponse(responseBuilder, exception);
//        } catch (PersistenceException exception) {
//            /* Persistence Exception */
//            ResponseTemplates.persistenceExceptionResponse(responseBuilder, exception);
//        } finally {
//            responseBuilder.setAllowMethods(HttpMethodsKeys.POST);
//            responseBuilder.setAllowHeaders(headersKeys);
//            responseBuilder.setExposeHeaders(headersKeys);
//            responseBuilder.build();
//        }
    }
}