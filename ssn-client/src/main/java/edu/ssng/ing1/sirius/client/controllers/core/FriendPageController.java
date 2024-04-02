package edu.ssng.ing1.sirius.client.controllers.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

import edu.ssng.ing1.BeFriend;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.business.dto.Students;
import edu.ssng.ing1.sirius.client.requests.friend.FriendCommonRequest;
import edu.ssng.ing1.sirius.client.router.Router;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class FriendPageController implements Initializable {

    @FXML
    private FlowPane invitationZone;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Students students = FriendCommonRequest.selectFriends(new BeFriend(2, 0));
            for (Student student : students.getStudents()) {
                FXMLLoader loader = Router.getInstance().getParentNode("friend-invitation");
                AnchorPane pane = (AnchorPane) loader.load();
                FriendInvitationController fr = loader.getController();
                fr.getName().setText(student.getFamilyname() + " " + student.getFirstname());
                Image image = new Image(convertBytesToInputStream(student.getProfileImageStream()));
                fr.getProfileImage().setImage(image);
                invitationZone.getChildren().add(pane);
            }
        } catch (NullPointerException | IOException | InterruptedException e) {
            System.out.println("erreur");
            System.out.println(e.getMessage());
        }
    }

    public static InputStream toInputStream(String base64String) {
        byte[] byteArray = Base64.getDecoder().decode(base64String);
        return new ByteArrayInputStream(byteArray);
    }
    private static InputStream convertBytesToInputStream(byte[] byteArray) {
        return new ByteArrayInputStream(byteArray);
    }
}
