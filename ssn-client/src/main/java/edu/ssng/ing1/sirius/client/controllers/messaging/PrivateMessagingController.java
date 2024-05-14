package edu.ssng.ing1.sirius.client.controllers.messaging;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import edu.ssng.ing1.sirius.business.dto.Message;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.controllers.commons.CommonsClient;
import edu.ssng.ing1.sirius.client.controllers.commons.UserInfo;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.requests.messages.CommonsMessageRequest;

public class PrivateMessagingController implements Initializable, StudentBtnAction {
        private static Student activeStudent;
        private static Boolean isFirstOpening = true;

        @FXML
        private Label fileIsChooseLabel;

        @FXML
        private Button addImageToMessageBtn;

        @FXML
        private VBox allFriends;

        @FXML
        private FontIcon isConnectedStudentInfo;

        @FXML
        private BorderPane borderPane;

        @FXML
        private Button buttonSend;

        @FXML
        private ScrollPane concversationArea;

        @FXML
        private VBox currentListOfMessages;

        @FXML
        private HBox currentUserChatArea;

        @FXML
        private Circle currentUserChatImage;

        @FXML
        private Label currentUserChatNameLabel;

        @FXML
        private Label currentUserSchool;

        @FXML
        private VBox hidderConversationArea;

        @FXML
        private ImageView logoApp;

        @FXML
        private TextArea messageBox;

        @FXML
        private Label myFriendSince;

        @FXML
        private Label onlineCountLabel;

        @FXML
        private HBox onlineUsersHbox;

        @FXML
        private Circle profileImageRight;

        @FXML
        private HBox sendingArea;

        @FXML
        private VBox studentInfoArea;

        @FXML
        private Label currentUserName;

        ImageFileChooser imageFileChooser = new ImageFileChooser();

        private byte[] currentimageBytes = null;

        @FXML
        void sendButtonAction(ActionEvent event) {
                int id_sender = UserInfo.getUser().getId_student();
                int id_receiver = activeStudent.getId_student();
                String message = messageBox.getText();
                Message msg = new Message(12, id_sender, id_receiver, message, currentimageBytes,
                                Timestamp.valueOf("2024-05-01 10:55:00"));
                try {
                        Object result = CommonsMessageRequest.sendMessage(msg);
                        
                        if (result instanceof Message) {
                                messageBox.clear();
                                currentListOfMessages.getChildren().add(new PrivateMessage((Message)result));
                                fileIsChooseLabel.setVisible(false);
                                concversationArea.setVvalue(1);
                                currentimageBytes = null;
                        }
                        else {
                                System.out.println("Error while sending message 1 ");
                        }
                } catch (NullPointerException | IOException | InterruptedException e) {
                        System.out.println("Error while sending message 2 ");
                }

        }

        @FXML
        void sendMethod(KeyEvent event) {

        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
                try {
                        fileIsChooseLabel.setVisible(false);
                        hideAllArea(true);
                        MessagingUpdater.getInstance(this, allFriends, currentListOfMessages); // intitialise la liste
                                                                                               // des amis
                        MessagingUpdater.updateStudentList(); // met à jour la liste des amis
                        CommonsClient.setImageOnClip(currentUserChatImage, "media/images/profil.jpg");
                        logoApp.setImage(CommonsClient.getImage(CommonsClient.logoApppath));
                        CommonsClient.setImageOnClip(profileImageRight, "media/images/profil.jpg");
                        fileIsChooseLabel.setVisible(false);

                        addImageToMessageBtn.setOnAction(e -> {
                                File selectedFile = imageFileChooser.showOpenDialog(Router.getInstance().getStage());
                                if (selectedFile != null) {
                                        try {
                                                InputStream selectedImageinputStream = null;

                                                selectedImageinputStream = imageFileChooser
                                                                .openInputStream(selectedFile);
                                                currentimageBytes = CommonsClient
                                                                .encodeInputStream(selectedImageinputStream);
                                                Image image = new Image(new ByteArrayInputStream(currentimageBytes));
                                                selectedImageinputStream.close();
                                                fileIsChooseLabel.setVisible(true);
                                        } catch (IOException ex) {
                                                ex.printStackTrace();
                                        }
                                }
                        });

                } catch (Exception e) {
                        e.printStackTrace();
                }

        }

        public void setEventStudentActive(FriendBtn fbutton) {
                Button btn = fbutton.getButton();
                btn.setOnAction(event -> {
                        activeStudent = fbutton.getStudent();
                        CommonsClient.setImageOnClip(profileImageRight, activeStudent.getProfileImageStream());
                        currentUserChatNameLabel
                                        .setText(activeStudent.getFamilyname() + " " + activeStudent.getFirstname());
                        currentUserSchool.setText(activeStudent.getUniversity());
                        myFriendSince.setText(
                                        "Ami depuis " + CommonsClient.durationOfEvent(fbutton.getBefriendDate()));
                        currentUserName.setText(activeStudent.getFamilyname() + " " + activeStudent.getFirstname());
                        isConnectedStudentInfo.setFill(fbutton.getIsOnline() ? javafx.scene.paint.Color.GREEN
                                        : javafx.scene.paint.Color.RED);
                        hideAllArea(false);
                        animateIfIsFirstOpening();
                        MessagingUpdater.updateMesageArea(activeStudent);
                });

        }

        private void hideAllArea(boolean hide) {
                currentUserChatArea.setVisible(!hide);
                hidderConversationArea.setVisible(hide);
                sendingArea.setVisible(!hide);
                concversationArea.setVisible(!hide);
                studentInfoArea.setVisible(!hide);
        }

        private void animateFadeIn(Duration duration, Node... nodes) {
                for (Node node : nodes) {
                        CommonsClient.animateFadeIn(node, duration);
                }
        }

        private void animateIfIsFirstOpening() {
                if (isFirstOpening) {
                        animateFadeIn(Duration.seconds(1), logoApp, allFriends, onlineUsersHbox);
                        isFirstOpening = false;
                }
        }

}
