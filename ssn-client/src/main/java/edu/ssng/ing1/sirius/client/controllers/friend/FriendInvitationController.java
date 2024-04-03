package edu.ssng.ing1.sirius.client.controllers.friend;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class FriendInvitationController {

    @FXML
    private Text durationOfInvitation;

    @FXML
    private Text formation;

    @FXML
    private Text name;

    @FXML
    private Text someFriend;
    @FXML
    private ImageView profileImage;

    public ImageView getProfileImage() {
        return profileImage;
    }

    public Text getDurationOfInvitation() {
        return durationOfInvitation;
    }

    public void setDurationOfInvitation(Text durationOfInvitation) {
        this.durationOfInvitation = durationOfInvitation;
    }

    public Text getFormation() {
        return formation;
    }

    public void setFormation(Text formation) {
        this.formation = formation;
    }

    public Text getName() {
        return name;
    }

    public void setName(Text name) {
        this.name = name;
    }

    public Text getSomeFriend() {
        return someFriend;
    }

    public void setSomeFriend(Text someFriend) {
        this.someFriend = someFriend;
    }

    public void setProfileImage(ImageView profileImage) {
        this.profileImage = profileImage;
    }
}
