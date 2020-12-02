package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.main;

import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.server.ApiServer;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.servlets.*;
import edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.logic.bussiness.LineBusinessService;
import org.eclipse.jetty.servlet.DefaultServlet;

/**
 * SERVER MANAGER API RUNNABLE.
 */
public class Main {
    public static void main(String...args) throws Exception {
        /* Initialize services */
        System.out.println("Internal services loading...");
        LineBusinessService.getInstance();
        System.out.println("Internal services are running.");
        /* Create the server */
        ApiServer server = new ApiServer(2525);
        /* Register Servlet's URI path. */
        server.registerServlet(DefaultServlet.class, "/");
        server.registerServlet(LineServlet.class, "/line");
        server.registerServlet(CallServlet.class, "/call");
        server.registerServlet(ServiceServlet.class, "/service");
        server.registerServlet(LineCustomerServlet.class, "/line_customer");
        server.registerServlet(LineCallServiceCustomerServlet.class, "/view");
        /* Start Http API */
        server.start();

    }
}
