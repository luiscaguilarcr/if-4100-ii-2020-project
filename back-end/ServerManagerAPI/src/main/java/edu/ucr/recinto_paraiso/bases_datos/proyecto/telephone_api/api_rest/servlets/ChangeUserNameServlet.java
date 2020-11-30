package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * URI end-point: /user/name
 */
public class ChangeUserNameServlet extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
//
//        final String SESSION_TOKEN = req.getHeader("session_token");
//
//        final String IP = req.getHeader("ip");
//        final Integer USER_ID = req.getIntHeader("user_id");
//        final String FIRSTNAME = req.getParameter("first_name");
//        final String LASTNAME = req.getParameter("last_name");
//        final String EMAIL = req.getParameter("email");
//        final String PASSWORD = req.getParameter("password");
//
//        try {
//            if (SESSION_TOKEN == null || SESSION_TOKEN.equals("")) {                                    /* CLIENT-TOKEN NOT PROVIDED */
//                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);                                     /* HTTP response code */
//                resp.setHeader("valid_authorization", "false");
//                resp.setHeader("error_message", "The [SESSION-TOKEN] is not provided.");
//                resp.setIntHeader("error_code", TokenService.SESSION_TOKEN_NOT_PROVIDED);
//            } else {
//                final LoginSession loginSession = new LoginSession(USER_ID, SESSION_TOKEN, IP);
//
//                if (!LoginProcessService.getInstance().validateSession(loginSession)) {
//                    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);                                    /* HTTP response code */
//                    resp.setHeader("valid_authorization", "false");
//                    resp.setHeader("error_message", "Invalid [SESSION-TOKEN].");
//                    resp.setIntHeader("error_code", TokenService.SESSION_TOKEN_INVALID);
//                } else {
//                    if(UserBusinessService.getInstance().changeName(new UserBuilder()
//                            .setId(USER_ID)
//                            .setEmail(EMAIL)
//                            .setPassword(PASSWORD)
//                            .setFirstName("")
//                            .setLastName("")
//                            .build(), FIRSTNAME, LASTNAME)){
//                        resp.setStatus(HttpServletResponse.SC_OK);                                      /* HTTP response code */
//                        resp.setHeader("valid_authorization", "true");
//                    } else {
//                        throw new BusinessException("Name not changed.", 0);
//                    }
//                }
//            }
//        } catch (final BusinessException exception) {
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);             /* HTTP response code */
//            resp.setHeader("valid_authorization", "true");
//            resp.setHeader("completed", "false");
//            resp.setHeader("error_message", exception.getMessage());
//            resp.setIntHeader("error_code", exception.getCode());
//        } catch (final IllegalArgumentException ignored) {                                              /* UUID PARSED ERROR */
//            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);                                        /* HTTP response code */
//            resp.setHeader("valid_authorization", "false");
//            resp.setHeader("error_message", "Invalid [SESSION-TOKEN]");
//            resp.setIntHeader("error_code", TokenService.SESSION_TOKEN_INVALID);
//        } catch (PersistenceException | SecurityException e) {
//            e.printStackTrace();
//        }

    }
}
