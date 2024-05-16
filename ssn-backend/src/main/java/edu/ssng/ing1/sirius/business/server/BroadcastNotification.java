package edu.ssng.ing1.sirius.business.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.ssng.ing1.sirius.business.server.notifyProcess.NotificationType;
import edu.ssng.ing1.sirius.commons.Notification;

public class BroadcastNotification {

    private final static String LoggingLabel = "B r o a d c a s t - N o t i f i c a t i on";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);

    static synchronized void broadcast(String type, Set<String> receivers, String... messageArguments)
            throws JsonProcessingException {
        logger.info("Broadcasting message to " + receivers.size() + " friends.");
        Notification notification = new Notification();
        NotificationType notificationType = NotificationType.valueOf(type);

        notification.setMessage(notificationType.getMessage(messageArguments));
        notification.setOrder(type);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        byte[] notifTosend = objectMapper.writeValueAsBytes(notification);

        for (String receiver : receivers) {
            Set<String> ipReceivers = ConnectedStudent
                    .getStudentConnectedemailHashmap().get(receiver);
            logger.info("Address found : " + ipReceivers);

            for (String ipReceiver : ipReceivers) {
                tryconnection(ipReceiver, notifTosend, receiver);
            }

        }

    }

    static synchronized void broadcast(String type, Set<String> receivers, Object object, String... messageArguments)
            throws JsonProcessingException {
        logger.info("Broadcasting message to " + receivers.size() + " friends.");
        Notification notification = new Notification();
        NotificationType notificationType = NotificationType.valueOf(type);

        notification.setMessage(notificationType.getMessage(messageArguments));
        notification.setOrder(type);
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String bject = objectMapper.writeValueAsString(object);

        notification.setBody(bject);
        byte[] notifTosend = objectMapper.writeValueAsBytes(notification);

        for (String receiver : receivers) {
            Set<String> ipReceivers = ConnectedStudent
                    .getStudentConnectedemailHashmap().get(receiver);
            logger.info("Address found : " + ipReceivers);

            for (String ipReceiver : ipReceivers) {
                tryconnection(ipReceiver, notifTosend, receiver);
            }

        }

    }

    public static void tryconnection(String ipReceiver, byte[] notifTosend, String receiverEmail) {

        try (Socket sendSocket = new Socket(ipReceiver, 5461)) {
            if (sendSocket != null) {
                try {
                    logger.info("le socket d'envoie est " + sendSocket);
                    OutputStream outputStream = sendSocket.getOutputStream();
                    outputStream.write(notifTosend);
                    logger.info("Message sent to " + receiverEmail);

                    sendSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (ConnectException e) {

            Thread tryagainThread = new Thread(() -> {
                try {

                    Thread.sleep(3000);

                    try (Socket sendSocket = new Socket(ipReceiver, 5461)) {
                        if (sendSocket != null) {

                            logger.info("le socket d'envoie est " + sendSocket);
                            OutputStream outputStream = sendSocket.getOutputStream();
                            outputStream.write(notifTosend);
                            logger.info("Message sent to " + receiverEmail);
                            sendSocket.close();

                        }
                    } catch (ConnectException e2) {
                        logger.debug(" Student disconnect {} ", receiverEmail);

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch (InterruptedException i) {
                    i.printStackTrace();
                }
            });
            tryagainThread.start();

        } catch (JsonProcessingException e) {

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // sendSocket.close();
        }

    }
    // static synchronized void broadcast(Query message, Set<String> friends, Object
    // object) {
    // System.out.println("Broadcasting message to " + friends.size() + "
    // friends.");
    // for (String receiver : friends) {
    // Socket friendSocket = connectedUsers.get(receiver);
    // if (friendSocket != null) {
    // try {
    // System.out.println(friendSocket);
    // OutputStream outputStream = friendSocket.getOutputStream();
    // outputStream.write(message.getBytes());
    // System.out.println("Message sent to " + receiver);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
    // }
    // }

}
