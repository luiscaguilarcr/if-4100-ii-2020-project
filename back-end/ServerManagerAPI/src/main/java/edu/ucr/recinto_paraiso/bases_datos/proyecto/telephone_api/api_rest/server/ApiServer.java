package edu.ucr.recinto_paraiso.bases_datos.proyecto.telephone_api.api_rest.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import javax.servlet.http.HttpServlet;

/**
 * Routes http request with internal services.
 */
public class ApiServer {
    private final ServletHandler handler;
    private final Integer port;

    public ApiServer(Integer port) {
        this.port = port;
        handler = new ServletHandler();
    }

    /**
     * Initialize the server operation.
     * @throws Exception exception.
     */
    public void start() throws Exception {
        /* Configuration */
        final int maxThreads = 100;
        final int minThreads = 10;
        final int idleTimeout = 120;
        /* Threads */
        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);
        /* Server Configuration */
        Server server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.setConnectors(new Connector[] {connector});
        server.setHandler(handler);
        /* Server Initialization */
        server.start();
        server.join();
    }
    public void registerServlet(Class<? extends HttpServlet> servlet, String path) {
        handler.addServletWithMapping(servlet, path);
    }
}
