package edu.ssng.ing1.sirius.client.notificationManagement.business;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.business.dto.Activite;
import edu.ssng.ing1.sirius.business.dto.BeFriend;
import edu.ssng.ing1.sirius.business.dto.Message;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.controllers.commons.HomeController;
import edu.ssng.ing1.sirius.client.controllers.commons.Initializer;
import edu.ssng.ing1.sirius.client.controllers.createActivity.SeeMyActivityController;
import edu.ssng.ing1.sirius.client.controllers.friend.FriendUpdater;
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
            case "ACCEPTED_ACTIVITY":
                acceptActivity(Notify);
            case "NEW_MESSAGE":
                getNewMessage(Notify);
                break;
            case "DENY_INVITATION":
                denyActivity(Notify);
            case "ACCEPTED_INVITATION":
                notifyAcceptedInvitation(Notify);
                break;
            case "DEMAND_INVITATION":
                notifyDemandFriend(Notify);

                break;
            default:
                break;
        }
    }

    private void notifyDemandFriend(Notification notification) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
            BeFriend friend = mapper.readValue(notification.getBody(), BeFriend.class);
            friend.setStatus("no reponse");
            Initializer.getFriends().add(friend);
        } catch (IOException e) {
            LoggerFactory.getLogger(SsnNotifyService.class).error("error", e);
        }

    }

    private void notifyAcceptedInvitation(Notification notification) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
            BeFriend friend = mapper.readValue(notification.getBody(), BeFriend.class);
            friend.setStatus("accepted");
            Initializer.getFriends().add(friend);
            FriendUpdater.updateAcceptedFriend();
            FriendUpdater.updateLimitedDemandedFriend();
           
        } catch (IOException e) {
            LoggerFactory.getLogger(SsnNotifyService.class).error("error", e);
        }
    }

    private void getNewMessage(Notification notification) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
            Message newMessage = mapper.readValue(notification.getBody(), Message.class);
            Initializer.getMessages().getMessages().add(newMessage);
            MessagingUpdater.addMessageInArea(newMessage);
            System.out.println(newMessage.toString());
        } catch (IOException e) {
            LoggerFactory.getLogger(SsnNotifyService.class).error("error", e);
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

    public void acceptActivity(Notification notification) {
        HomeController.getNotificationToBedisplayed().add(notification);
        Platform.runLater(() -> HomeController.displayOnnotifPanel());
    }

    public void denyActivity(Notification notification) {
        HomeController.getNotificationToBedisplayed().add(notification);
        Platform.runLater(() -> HomeController.displayOnnotifPanel());
    }

    public void inviteActicity(Notification notification) throws JsonParseException, JsonMappingException, IOException {

        HomeController.getNotificationToBedisplayed().add(notification);
        Platform.runLater(() -> HomeController.displayOnnotifPanel());
        objectMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        Activite activite = objectMapper.readValue(notification.getBody(), Activite.class);

        Platform.runLater(() -> {
            SeeMyActivityController.getActiviteInvitationSet().add(activite);
            SeeMyActivityController.displayInvitation(activite);
        });

    }

    public void newActivity(Notification notification) {

    }

    public void connectUser(Notification notification) {

    }
}