package edu.ssng.ing1.sirius.client.controllers.messaging;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.javafx.FontIcon;

public class FriendBtnController {

    @FXML
    private Button btn;

    @FXML
    private Label name;

    @FXML
    private Circle profileImage;

    @FXML
    private FontIcon status;
    

    public Button getBtn() {
        return this.btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public Label getName() {
        return this.name;
    }

    public void setName(Label name) {
        this.name = name;
    }

    public Circle getProfileImage() {
        return this.profileImage;
    }

    public void setProfileImage(Circle profileImage) {
        this.profileImage = profileImage;
    }

    public FontIcon getStatus() {
        return this.status;
    }

    public void setStatus(FontIcon status) {
        this.status = status;
    }


}

