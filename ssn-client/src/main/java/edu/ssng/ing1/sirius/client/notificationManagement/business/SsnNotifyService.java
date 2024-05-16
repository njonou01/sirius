package edu.ssng.ing1.sirius.client.notificationManagement.business;

import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.business.dto.Message;
import edu.ssng.ing1.sirius.client.controllers.commons.HomeController;
import edu.ssng.ing1.sirius.client.controllers.commons.Initializer;
import edu.ssng.ing1.sirius.client.controllers.messaging.MessagingUpdater;
import edu.ssng.ing1.sirius.commons.Notification;
import javafx.application.Platform;

public class SsnNotifyService {

    ObjectMapper objectMapper = new ObjectMapper();

    public void dispatch(Notification Notify)
            throws JsonParseException, JsonMappingException, IOException {

        switch (Notify.getOrder()) {

            case "NEW_CONNECTION":
                notifyConnection(Notify);
                break;
            case "CONNECT_USER":
                connectUser(Notify);
                break;
            case "INVITE_ACTIVITY":
                inviteActicity(Notify);
                break;

            case "NEW_ACTIVITY":
                newActivity(Notify);
                break;

            case "NEW_MESSAGE":
                getNewMessage(Notify);
                break;

            default:
                break;
        }
    }

    private void getNewMessage(Notification notification) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Message newMessage = mapper.readValue(notification.getBody(), Message.class);
            Initializer.getMessages().getMessages().add(newMessage);
            MessagingUpdater.addMessageInArea(newMessage);
        } catch (IOException e) {

        }

    }

    public void notifyConnection(Notification notification) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(date);
        notification.setHoursReceive(dateString);

        HomeController.getNotificationToBedisplayed().add(notification);
        Platform.runLater(() -> HomeController.displayOnnotifPanel());

    }

    public void inviteActicity(Notification notification) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(date);
        notification.setHoursReceive(dateString);

        HomeController.getNotificationToBedisplayed().add(notification);
        Platform.runLater(() -> HomeController.displayOnnotifPanel());

    }

    public void newActivity(Notification notification) {

    }

    public void connectUser(Notification notification) {

    }

}
