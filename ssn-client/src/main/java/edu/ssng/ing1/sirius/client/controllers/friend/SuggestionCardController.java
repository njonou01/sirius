package edu.ssng.ing1.sirius.client.controllers.friend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class SuggestionCardController {

    @FXML
    private Button closeBtn;

    @FXML
    private Text commonFriendsCount;

    @FXML
    private Button demandFriendBtn;

    @FXML
    private Button fullName;

    @FXML
    private ImageView image;

    @FXML

    public Button getCloseBtn() {
        return this.closeBtn;
    }

    public Text getCommonFriendsCount() {
        return this.commonFriendsCount;
    }

    public Button getDemandFriendBtn() {
        return this.demandFriendBtn;
    }

    public Button getFullName() {
        return this.fullName;
    }

    public ImageView getImage() {
        return this.image;
    }

    public Label getSchool() {
        return this.school;
    }
    @FXML
    private Label school;
    

}
