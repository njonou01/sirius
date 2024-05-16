package edu.ssng.ing1.sirius.client.notificationManagement;

import java.io.*;
import java.net.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ssng.ing1.sirius.client.commons.ConfigLoader;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;

public class ClientConnexion extends Thread {
    private final static String LoggingLabel = "N o t i f y - C l i e n t - c o n n e x i o n";
    private final static String networkConfigFile = "network.yaml";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private static NetworkConfig networkConfig;
    Socket clientSocket;
    private static Boolean isCloseSocket = true;
    private static ServerSocket serverSocket = null;
    // private final Set<RequestHandler> setHandlers = Collections
    // .synchronizedSet(new LinkedHashSet<RequestHandler>());

    // public static void main(String[] args) {
    // try {
    // ClientConnexion clientConnexion= new
    // ClientConnexion("kshemwell0@4shared.com");
    // } catch (IOException e) {

    // logger.error(LoggingLabel, e);
    // }
    // }

    public ClientConnexion() throws IOException {
        networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
    }

    @Override
    public void run() {

        try {
            logger.info("Ready To get All Notification in port {}....", networkConfig.getTcpportServerNotify());
            serverSocket = new ServerSocket(networkConfig.getTcpportServerNotify());
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    serverSocket.close();
                    logger.info("Client  notification stopped.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("new notification {}" + clientSocket);
                new NotifyHandler(clientSocket).start();
                if (!isCloseSocket)
                    break;
            }

        } catch (IOException e) {
            // e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void closersocket() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.error("Error", e);
        }
        isCloseSocket = false;
    }
}
