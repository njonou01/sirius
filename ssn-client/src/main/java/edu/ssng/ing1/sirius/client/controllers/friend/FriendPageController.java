package edu.ssng.ing1.sirius.client.controllers.friend;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FriendPageController implements Initializable {

    @FXML
    private Button allFriendBtn;

    @FXML
    private Button birthdayBtn;

    @FXML
    private VBox allFriendPanel;

    @FXML
    private Button friendHomeBtn;

    @FXML
    private Button friendInvitationsBtn;

    @FXML
    private Button friendSuggestionsBtn;

    @FXML
    private VBox homePanel;

    @FXML
    private VBox invatationPane;

    @FXML
    private FlowPane invitationZone;

    @FXML
    private FlowPane invitationZone1;

    @FXML
    private VBox sugggestionPanel;

    @FXML
    private StackPane workStackZone;
    Map<Button, VBox> btnmapper = new HashMap<Button, VBox>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnmapper.put(allFriendBtn, allFriendPanel);
        btnmapper.put(friendHomeBtn, homePanel);
        btnmapper.put(friendInvitationsBtn, invatationPane);
        btnmapper.put(friendSuggestionsBtn, sugggestionPanel);
        initializeBtn(this.allFriendBtn,  this.friendHomeBtn,
                this.friendInvitationsBtn, this.friendSuggestionsBtn);
    }

    public void initializeBtn(Button... btnsListf) {
        for (Button button : btnsListf) {
            button.setOnAction(event -> {
                for (VBox entry : this.btnmapper.values()) {
                    entry.setVisible(false);
                }
                btnmapper.get(button).setVisible(true);
            });
        }
    }

    private static InputStream convertBytesToInputStream(byte[] byteArray) {
        return new ByteArrayInputStream(byteArray);
    }
}
