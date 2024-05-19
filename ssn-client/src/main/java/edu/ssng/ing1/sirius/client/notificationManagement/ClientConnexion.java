package edu.ssng.ing1.sirius.client.notificationManagement;

import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.client.commons.ConfigLoader;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;

public class ClientConnexion extends Thread {
    private final static String LoggingLabel = "N o t i f y - C l i e n t - c o n n e x i o n";
    private final static String networkConfigFile = "network.yaml";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private static NetworkConfig networkConfig;
    Socket clientSocket ;
    public static Boolean isCloseSocket=true;
    private static ServerSocket serverSocket = null;
    // private final Set<RequestHandler> setHandlers = Collections
    //         .synchronizedSet(new LinkedHashSet<RequestHandler>());

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
            System.out.println("Ready To get All Notification....");
            System.out.println(networkConfig.getTcpportServerNotify());

            serverSocket = new ServerSocket(networkConfig.getTcpportServerNotify());
         
            int i = 0;

            while (isCloseSocket) {
                System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPP");
                System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPP");
                System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPP<<");
                System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPP<<");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLL<<");
                System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLL<<");
                System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLL<<");
                System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLL<<");
                System.out.println("Une nouvelle notif :" + i);
                System.out.println(clientSocket);
                
                i++;
                new NotifyHandler(clientSocket).start();
                
            }


        } catch (IOException e) {
            if (!isCloseSocket) {
                System.out.println("Server stopped.");
            } else {
                e.printStackTrace();
            }
        } finally {
            if (serverSocket != null) {
                try {
                    System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
                    System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
                    System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void closersocket(){
        isCloseSocket = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
                System.out.println("Server socket closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
