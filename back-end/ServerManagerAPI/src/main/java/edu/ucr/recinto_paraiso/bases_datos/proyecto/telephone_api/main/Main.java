package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.main;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.server.ApiServer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets.*;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.RemoteServerBusinessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.SshBusinessService;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.UserBusinessService;
import org.eclipse.jetty.servlet.DefaultServlet;

/**
 * SERVER MANAGER API RUNNABLE.
 */
public class Main {
    public static void main(String...args) throws Exception {
        /* Initialize services */
        System.out.println("Internal services loading...");
        UserBusinessService.getInstance();
        RemoteServerBusinessService.getInstance();
        SshBusinessService.getInstance();
        System.out.println("Internal services are running.");
        /* Create the server */
        ApiServer server = new ApiServer(2525);
        /* Register Servlet's URI path. */
        server.registerServlet(DefaultServlet.class, "/");
        server.registerServlet(AuthorizationServlet.class, "/authorize");
        server.registerServlet(RegisterServlet.class, "/user/register");
        server.registerServlet(LoginServlet.class, "/user/login");
        server.registerServlet(LogoutServlet.class, "/user/logout");
        server.registerServlet(ChangeUserNameServlet.class, "/user/name");
        server.registerServlet(ChangeUserEmailServlet.class, "/user/email");
        server.registerServlet(ChangeUserPasswordServlet.class, "/user/password");
        server.registerServlet(RemoteServerServlet.class, "/user/remote_server");
        server.registerServlet(InitializeSSHServlet.class, "/user/remote_server/ssh/init");
        server.registerServlet(ExecuteSSHCommandServlet.class, "/user/remote_server/ssh/execute_command");
        server.registerServlet(FinishSSHServlet.class, "/user/remote_server/ssh");
        server.registerServlet(LogServlet.class, "/user/logs");
        /* Start Http API */
        server.start();

    }
}
