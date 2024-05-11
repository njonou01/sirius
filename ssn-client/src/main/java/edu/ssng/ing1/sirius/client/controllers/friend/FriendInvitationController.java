package edu.ssng.ing1.sirius.client.controllers.friend;

import java.util.Map;

import edu.ssng.ing1.sirius.requests.friend.FriendCommonRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class FriendInvitationController {



    @FXML
    private Button acceptBtn;

    public Button getAcceptBtn() {
        return acceptBtn;
    }

    @FXML
    private Text durationOfInvitation;

    @FXML
    private Text formation;

    @FXML
    private Button ignoreBtn;

    public Button getIgnoreBtn() {
        return ignoreBtn;
    }

    @FXML
    private Text name;

    @FXML
    private ImageView profileImage;

    @FXML
    private Text someFriend;

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
