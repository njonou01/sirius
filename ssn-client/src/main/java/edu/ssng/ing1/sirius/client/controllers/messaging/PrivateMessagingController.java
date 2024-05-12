package edu.ssng.ing1.sirius.client.controllers.messaging;

import edu.ssng.ing1.sirius.business.dto.Message;
import edu.ssng.ing1.sirius.business.dto.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class PrivateMessagingController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button buttonSend;

    @FXML
    private ListView<Message> chatPane;

    @FXML
    private TextArea messageBox;

    @FXML
    private Label onlineCountLabel;

    @FXML
    private HBox onlineUsersHbox;

    @FXML
    private ImageView userImageView;

    @FXML
    private ListView<Student> userList;

    @FXML
    private Label usernameLabel;

    @FXML
    void sendButtonAction(ActionEvent event) {

    }

    @FXML
    void sendMethod(KeyEvent event) {

    }

}
