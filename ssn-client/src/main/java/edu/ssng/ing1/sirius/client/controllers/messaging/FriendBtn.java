package edu.ssng.ing1.sirius.client.controllers.messaging;

import java.io.IOException;

import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.router.Router;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class FriendBtn extends HBox {
    private Student student;
    private Boolean isOnline;

    public FriendBtn(Student student, Boolean isOnline) {
        this.student = student;
        this.isOnline = isOnline;
        try {
            FXMLLoader loader = Router.getInstance().getParentNode("friendBtn");
            loader.load();
            Button button = (Button) loader.getNamespace().get("btn");
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
}