package com.moneytransfer;

import com.moneytransfer.common.jpa.Account;
import com.moneytransfer.common.jpa.AccountRecipients;
import com.moneytransfer.common.jpa.User;
import com.moneytransfer.common.utils.EntityManagerUtil;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ServerProperties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.ArrayList;

public class App {
    public static void main(String args[]) throws Exception {

        // start the web interface of H2 Database
        // web interface can be reached by http://localhost:7071

        org.h2.tools.Server h2Server = startH2Db();

        Server jettyServer = initializeJettyServer();

        initializeDatabase();

        try {
            // jetty is started here
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
            h2Server.stop();
        }
    }

    public static org.h2.tools.Server startH2Db() throws Exception {
        org.h2.tools.Server server = org.h2.tools.Server.createWebServer(new String[]{"-web","-webAllowOthers","-webPort","9019"}).start();
        return server;

    }

    public static Server initializeJettyServer() throws Exception {
        // create servlet context
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        servletContextHandler.setContextPath("/");
        int port = 9090;
        // create jetty server
        Server jettyServer = new Server(port);
        jettyServer.setHandler(servletContextHandler);

        // add the jersey servlet to jetty servlet context
        ServletHolder jerseyServlet = servletContextHandler.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");

        // make sure jersey to scan the packages of your project
        jerseyServlet.setInitParameter(ServerProperties.PROVIDER_PACKAGES, "com.moneytransfer");
        // adding jackson to convert responses to JSON format
        jerseyServlet.setInitParameter(ServerProperties.PROVIDER_CLASSNAMES, "org.glassfish.jersey.jackson.JacksonFeature");
        return jettyServer;
    }

    public static void initializeDatabase() throws Exception {
        EntityManagerUtil.initializeEntityManagerFactory("money-transfer-persistence");
          EntityManager manager = EntityManagerUtil.getEntityManager();


        User user = new User();
        user.setAddress("UK");
        user.setFirstName("Tyrion");
        user.setLastName("Lannister");
        user.setVersion(0);
        manager.getTransaction().begin();
        manager.persist(user);
        Account account = new Account();
        account.setUser(user);
        account.setAccountType(1);
        account.setBalance(BigDecimal.valueOf(500000));
        account.setCurrency("GBP");
        account.setVersion(0);
        AccountRecipients recipient1 = new AccountRecipients();
        recipient1.setAccount(account);
        recipient1.setCurrency("GBP");
        Account recipientAccount = (Account)manager.createQuery("from Account where accountNumber = 4").getSingleResult();
        recipient1.setRecipientAccount(recipientAccount);
        recipient1.setVersion(0);
        account.setAccountRecipients(new ArrayList<AccountRecipients>(){{add(recipient1);}});
        manager.persist(account);
        manager.flush();
        manager.getTransaction().commit();

        manager.close();
    }
}
