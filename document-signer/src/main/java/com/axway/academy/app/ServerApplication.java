package com.axway.academy.app;

import com.axway.academy.util.ServiceUtil;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class ServerApplication extends ResourceConfig{
    public static void main(String[] args) {

        ResourceConfig config = new ResourceConfig();
        config.packages("com.axway.academy").register(MultiPartFeature.class);
        config.register(MultiPartFeature.class);
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));
        Server server = new Server(9999);

        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.addServlet(servlet, "/*");

        SessionHandler sessions = new SessionHandler();
        context.setSessionHandler(sessions);
        server.setHandler(context);

        ServiceUtil serviceUtil = new ServiceUtil();
        serviceUtil.setDaemon(true);
        serviceUtil.start();
       // context.handle(serviceUtil);

        try {
            server.start();
            server.join();

        } catch (Exception e) {
            //TODO deal with this exception
            System.out.println();
        }
    }
}
