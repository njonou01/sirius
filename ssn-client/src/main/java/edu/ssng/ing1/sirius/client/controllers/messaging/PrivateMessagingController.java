package edu.ssng.ing1.sirius.client.controllers.messaging;

import java.net.URL;
import java.util.ResourceBundle;

import ch.qos.logback.core.net.server.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import edu.ssng.ing1.sirius.client.controllers.commons.CommonsClient;
import edu.ssng.ing1.sirius.client.controllers.messaging.PrivateMessage.MessageType;
public class PrivateMessagingController implements Initializable {

    @FXML
    private VBox allFriends;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button buttonSend;

    @FXML
    private HBox currentUserChat;

    @FXML
    private Circle currentUserChatImage;

    @FXML
    private Label currentUserChatNameLabel;

    @FXML
    private ImageView logoApp;

    @FXML
    private TextArea messageBox;

    @FXML
    private Label onlineCountLabel;

    @FXML
    private HBox onlineUsersHbox;

    @FXML
    private Circle profileImageRight;

    @FXML
    private VBox currentListOfMessages;

    @FXML
    void sendButtonAction(ActionEvent event) {

    }

    @FXML
    void sendMethod(KeyEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CommonsClient.setImageOnClip(currentUserChatImage, "media/images/profil.jpg");
        logoApp.setImage(CommonsClient.getImage(CommonsClient.logoApppath));
        CommonsClient.setImageOnClip(profileImageRight, "media/images/profil.jpg");
        currentListOfMessages.getChildren().add(new PrivateMessage(MessageType.ME));
        currentListOfMessages.getChildren().add(new PrivateMessage(MessageType.ME));
        currentListOfMessages.getChildren().add(new PrivateMessage(MessageType.FRIEND_MESSAGE));
        currentListOfMessages.getChildren().add(new PrivateMessage(MessageType.ME));
        currentListOfMessages.getChildren().add(new PrivateMessage(MessageType.FRIEND_MESSAGE));
        currentListOfMessages.getChildren().add(new PrivateMessage(MessageType.FRIEND_MESSAGE));
        currentListOfMessages.getChildren().add(new PrivateMessage(MessageType.ME));
    }

}
