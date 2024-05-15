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
    private static Boolean isCloseSocket=true;
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
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    serverSocket.close();
                    System.out.println("Server stopped.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
            int i = 0;

            while (true) {
                System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPP");
                System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPP");
                System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPP<<");
                System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPP<<");
                Socket clientSocket = serverSocket.accept();
                System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLL<<");
                System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLL<<");
                System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLL<<");
                System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLL<<");
                System.out.println("Une nouvelle notif :" + i);
                System.out.println(clientSocket);
                
                i++;
                new NotifyHandler(clientSocket).start();
                if (!isCloseSocket) break; 
            }


        } catch (IOException e) {
            // e.printStackTrace();
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
        try {
            serverSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        isCloseSocket=false;
    }
}