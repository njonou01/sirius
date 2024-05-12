package edu.ssng.ing1.sirius.client.controllers.messaging;

import java.io.IOException;
import java.sql.Timestamp;

import edu.ssng.ing1.sirius.business.dto.BeFriend;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.controllers.commons.CommonsClient;
import edu.ssng.ing1.sirius.client.controllers.commons.Initializer;
import edu.ssng.ing1.sirius.client.router.Router;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class FriendBtn extends HBox {
    private Student student;
    private Boolean isOnline;
    private Button button;

    public FriendBtn(Student student, Boolean isOnline) {
        this.student = student;
        this.isOnline = isOnline;
        try {
            FXMLLoader loader = Router.getInstance().getParentNode("btnfriend_msg");
            loader.load();
            FriendBtnController controller = loader.getController();
            controller.getName().setText(student.getFamilyname() + " " + student.getFirstname());
            CommonsClient.setImageOnClip(controller.getProfileImage(), student.getProfileImageStream());
            controller.getStatus().setFill(isOnline ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
            button = (Button) loader.getNamespace().get("btn");
            this.getChildren().setAll(button);
        } catch (IOException e) {

        }
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public Button getButton() {
        return button;
    }

    public Timestamp getBefriendDate() {
        for (BeFriend friend : Initializer.getFriends()) {
            if (friend.getSender().getId_student() == student.getId_student() ||
                    friend.getReceiver().getId_student() == student.getId_student()) {
                return friend.getBefriend_since();
            }
        }
        return null;
    }
}