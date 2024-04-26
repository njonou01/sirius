package edu.ssng.ing1.sirius.client.controllers.friend;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class FriendCardController {

    @FXML
    private Text durationOfInvitation;

    @FXML
    private Text formation;

    @FXML
    private Text name;

    @FXML
    private ImageView profileImage;

    public Text getDurationOfInvitation() {
        return durationOfInvitation;
    }

    public Text getFormation() {
        return formation;
    }

    public Text getName() {
        return name;
    }

    public ImageView getProfileImage() {
        return profileImage;
    }

}
