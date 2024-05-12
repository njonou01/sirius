package edu.ssng.ing1.sirius.client.controllers.commons;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.controlsfx.tools.Borders.Border;

import edu.ssng.ing1.sirius.business.dto.BeFriend;
import edu.ssng.ing1.sirius.business.dto.BeFriends;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.requests.friend.FriendCommonRequest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Initializer {
    public static BeFriends friends;

    public static BorderPane initInvitationPage() {
        FXMLLoader fxml = Router.getInstance().getParentNode("friend-page");
        System.out.println(fxml);
        try {
            return (BorderPane) fxml.load();
        } catch (IOException e) {
            return null;
        }
    }

    public static BorderPane initMessagingPage() {
        FXMLLoader fxml = Router.getInstance().getParentNode("messaging");
        try {
            BorderPane pane = (BorderPane) fxml.load();
            fxml.getController();

            return pane;
        } catch (IOException e) {
            return null;
        }
    }

    public static void invitationsFetcher() {
        try {
            int student_id = UserInfo.getUser().getId_student();
            friends = FriendCommonRequest.selectFriends(new Student(student_id));
        } catch (NullPointerException | IOException | InterruptedException e) {
            friends = new BeFriends();
        }
    }

    public static Set<BeFriend> getFriends() {
        Set<BeFriend> acceptedfriend = friends.getBefriends()
                .stream()
                .filter(f -> f.getStatus() == "accepted")
                .collect(Collectors.toSet());
        return acceptedfriend;
    }

    public static Set<BeFriend> friendshipRequestReceiver() {
        Set<BeFriend> friendshipRequest = friends.getBefriends()
                .stream()
                .filter(f -> f.getStatus() == "no reponse")
                .collect(Collectors.toSet());
        return friendshipRequest;
    }

}
