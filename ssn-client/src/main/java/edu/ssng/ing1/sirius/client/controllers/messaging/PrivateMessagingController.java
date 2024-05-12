package edu.ssng.ing1.sirius.client.controllers.messaging;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import ch.qos.logback.core.net.server.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
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

public class PrivateMessagingController implements Initializable {
        private static Student activeStudent;

        @FXML
        private VBox allFriends;

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

        @FXML
        void sendButtonAction(ActionEvent event) {

        }

        @FXML
        void sendMethod(KeyEvent event) {

        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
                try {
                        hideAllArea(true);
                        CommonsClient.setImageOnClip(currentUserChatImage, "media/images/profil.jpg");
                        logoApp.setImage(CommonsClient.getImage(CommonsClient.logoApppath));
                        CommonsClient.setImageOnClip(profileImageRight, "media/images/profil.jpg");
                        Message message1 = new Message(1, 952, 44,
                                        "Salut  gars ! J'ai rencontré un problème avec la configuration du serveur pour Sirius.",
                                        null,
                                        Timestamp.valueOf("2024-05-01 10:00:00"));
                        Message message2 = new Message(2, 44, 952, "Salut ! Quel est le problème exactement ?", null,
                                        Timestamp.valueOf("2024-05-01 10:05:00"));
                        Message message3 = new Message(3, 952, 44,
                                        "Je ne parviens pas à faire fonctionner la connexion à la base de données.",
                                        null,
                                        Timestamp.valueOf("2024-05-01 10:10:00"));
                        Message message4 = new Message(4, 44, 952,
                                        "As-tu vérifié les paramètres de configuration de la base de données dans le fichier de configuration ?",
                                        null, Timestamp.valueOf("2024-05-01 10:15:00"));
                        Message message5 = new Message(5, 952, 44,
                                        "Oui, mais je pense que j'ai manqué quelque chose. Peux-tu jeter un coup d'œil ?",
                                        null,
                                        Timestamp.valueOf("2024-05-01 10:20:00"));
                        Message message6 = new Message(6, 44, 952, "Bien sûr. Donne-moi quelques minutes.", null,
                                        Timestamp.valueOf("2024-05-01 10:25:00"));
                        Message message7 = new Message(7, 952, 44, "D'accord, merci !", null,
                                        Timestamp.valueOf("2024-05-01 10:30:00"));
                        Message message8 = new Message(8, 44, 952,
                                        "Je pense avoir trouvé le problème. Il y avait une erreur de syntaxe dans le fichier de configuration.",
                                        null, Timestamp.valueOf("2024-05-01 10:35:00"));
                        Message message9 = new Message(9, 952, 44,
                                        "Ah, d'accord. Je vais corriger ça. Merci beaucoup !", null,
                                        Timestamp.valueOf("2024-05-01 10:40:00"));
                        Message message10 = new Message(10, 44, 952,
                                        "Pas de soucis. Tiens-moi au courant si tu as besoin d'aide pour autre chose.",
                                        null,
                                        Timestamp.valueOf("2024-05-01 10:45:00"));
                        Message message11 = new Message(11, 952, 44, "Merci !", null,
                                        Timestamp.valueOf("2024-05-01 10:50:00"));
                        Message message12 = new Message(12, 44, 952, "Salut ! Comment ça avance de ton côté ?", null,
                                        Timestamp.valueOf("2024-05-01 10:55:00"));
                        Message message13 = new Message(13, 952, 44,
                                        "Tout roule ! J'ai résolu le problème et maintenant je suis en train de travailler sur l'interface utilisateur.",
                                        null, Timestamp.valueOf("2024-05-01 11:00:00"));
                        Message message14 = new Message(14, 44, 952, "Génial ! Tu as besoin d'un coup de main ?", null,
                                        Timestamp.valueOf("2024-05-01 11:05:00"));
                        Message message15 = new Message(15, 952, 44,
                                        "Non, je devrais m'en sortir. Mais merci quand même !", null,
                                        Timestamp.valueOf("2024-05-01 11:10:00"));
                        Message message16 = new Message(16, 44, 952, "OK, n'hésite pas si tu changes d'avis.", null,
                                        Timestamp.valueOf("2024-05-01 11:15:00"));
                        Message message17 = new Message(17, 952, 44,
                                        "C'est noté. Bon, je vais me replonger dans le code.", null,
                                        Timestamp.valueOf("2024-05-01 11:20:00"));
                        Message message18 = new Message(18, 44, 952, "Bonne chance !", null,
                                        Timestamp.valueOf("2024-05-01 11:25:00"));
                        Message message19 = new Message(19, 952, 44, "Merci !", null,
                                        Timestamp.valueOf("2024-05-01 11:30:00"));
                        Message message20 = new Message(20, 44, 952, "À plus tard !", null,
                                        Timestamp.valueOf("2024-05-01 11:35:00"));

                        currentListOfMessages.getChildren().add(new PrivateMessage(message1));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message2));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message3));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message4));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message5));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message6));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message7));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message8));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message9));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message10));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message11));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message12));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message13));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message14));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message15));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message16));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message17));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message18));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message19));
                        currentListOfMessages.getChildren().add(new PrivateMessage(message20));
                        for (int i = 0; i < 20; i++) {
                                FriendBtn btn = new FriendBtn(UserInfo.getUser(), false);
                                allFriends.getChildren().add(btn);
                                setEventStudentActive(btn);
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }

        }

        private void setEventStudentActive(FriendBtn fbutton) {
                Button btn = fbutton.getButton();
                btn.setOnAction(event -> {
                        activeStudent = fbutton.getStudent();
                        CommonsClient.setImageOnClip(profileImageRight, activeStudent.getProfileImageStream());
                        currentUserChatNameLabel
                                        .setText(activeStudent.getFamilyname() + " " + activeStudent.getFirstname());
                        currentUserSchool.setText(activeStudent.getUniversity());
                        myFriendSince.setText(
                                        "Amis depuis " + CommonsClient.durationOfEvent(fbutton.getBefriendDate()));
                        currentUserName.setText(activeStudent.getFamilyname() + " " + activeStudent.getFirstname());
                        hideAllArea(false);
                });

        }

        private void hideAllArea(boolean hide) {
                currentUserChatArea.setVisible(!hide);
                hidderConversationArea.setVisible(hide);
                sendingArea.setVisible(!hide);
                concversationArea.setVisible(!hide);
                studentInfoArea.setVisible(!hide);
                Duration delay = Duration.millis(500);
                CommonsClient.animateFadeIn(currentUserChatArea, delay);
                CommonsClient.animateFadeIn(hidderConversationArea, delay);
                CommonsClient.animateFadeIn(sendingArea, delay);
                CommonsClient.animateFadeIn(concversationArea, delay);
                CommonsClient.animateFadeIn(studentInfoArea, delay);

        }

}
