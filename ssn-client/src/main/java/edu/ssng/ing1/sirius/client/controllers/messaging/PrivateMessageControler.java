package edu.ssng.ing1.sirius.client.controllers.messaging;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class PrivateMessageControler {

    @FXML
    private Label message;

    @FXML
    private ImageView profileImage;

    public Label getMessage() {
        return this.message;
    }

    public void setMessage(Label message) {
        this.message = message;
    }

    public ImageView getProfileImage() {
        return this.profileImage;
    }

    public void setProfileImage(ImageView profileImage) {
        this.profileImage = profileImage;
    }

    public Label getSendingDate() {
        return this.sendingDate;
    }

    public void setSendingDate(Label sendingDate) {
        this.sendingDate = sendingDate;
    }

    @FXML
    private Label sendingDate;


    @FXML
    private ImageView image;

    public ImageView getImage() {
        return image;
    }


}
