package edu.ssng.ing1.sirius.client.controllers.friend;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import edu.ssng.ing1.sirius.business.dto.BeFriend;
import edu.ssng.ing1.sirius.business.dto.BeFriends;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.controllers.commons.UserInfo;
import edu.ssng.ing1.sirius.client.requests.friend.FriendCommonRequest;
import edu.ssng.ing1.sirius.client.router.Router;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FriendPageController implements Initializable {
    @FXML
    private Button allFriendBtn;
    @FXML
    private Button birthdayBtn;
    @FXML
    private ScrollPane  allFriendPanel;
    @FXML
    private Button friendHomeBtn;
    @FXML
    private Button friendInvitationsBtn;
    @FXML
    private Button friendSuggestionsBtn;
    @FXML
    private ScrollPane  homePanel;
    @FXML
    private ScrollPane invatationPane;
    @FXML
    private FlowPane invitationZone;
    @FXML
    private FlowPane invitationPanelZone;
    @FXML
    private ScrollPane  sugggestionPanel;
    @FXML
    private HBox invitationViewMore;
    @FXML
    private StackPane workStackZone;
    @FXML
    private FlowPane friendZone;
    @FXML
    private ScrollPane friendScroll;
    @FXML
    private Label numberOfFriends;
    Map<Button, ScrollPane> btnmapper = new HashMap<Button, ScrollPane>();
    private BeFriends friends;

    public FriendPageController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            int student_id = UserInfo.getUser().getId_student();
            friends  = FriendCommonRequest.selectFriends(new Student(student_id));
            System.out.println(friends.getBefriends().size());
        } catch (NullPointerException | IOException | InterruptedException e) {
            friends  = null;
        }
        btnmapper.put(allFriendBtn, allFriendPanel);
        btnmapper.put(friendHomeBtn, homePanel);
        btnmapper.put(friendInvitationsBtn, invatationPane);
        btnmapper.put(friendSuggestionsBtn, sugggestionPanel);
        initializeBtn(this.allFriendBtn, this.friendHomeBtn,
                this.friendInvitationsBtn, this.friendSuggestionsBtn);
        Button invViewMoreBtn = (Button) invitationViewMore.getChildren().get(0);
        invViewMoreBtn.setOnAction(event -> friendInvitationsBtn.fire());

        try {
            Set<BeFriend> friends_invitation = friends.getBefriends().stream()
                    .filter(friend -> "no reponse".equals(friend.getStatus()))
                    .collect(Collectors.toSet());
            List<AnchorPane> acceptedfriend = friends.getBefriends().stream()
                    .filter(friend -> "accepted".equals(friend.getStatus())).map(friend -> {
                        try {
                            Student student = friend.getSender();
                            FXMLLoader loader = Router.getInstance().getParentNode("friend-card");
                            AnchorPane pane = (AnchorPane) loader.load();
                            FriendCardController fr = loader.getController();
                            Image image = new Image(convertBytesToInputStream(student.getProfileImageStream()));
                            fr.getName().setText(student.getFamilyname() + " " + student.getFirstname());
                            fr.getProfileImage().setImage(image);
                            fr.getFormation().setText(student.getUniversity());
                            return pane;
                        } catch (Exception e) {
                            return new AnchorPane();
                        }
                    }).collect(Collectors.toList());
            friendZone.getChildren().setAll(acceptedfriend);

            numberOfFriends.setText(acceptedfriend.size() + " " + (acceptedfriend.size() <= 1 ? "ami(e)" : "ami(e)s"));

            if (friends_invitation.size() <= 5) {
                Pane parent = (Pane) invitationViewMore.getParent();
                parent.getChildren().remove(invitationViewMore);
            }
            invitationZone.getChildren().setAll(
                    friends_invitation.stream().limit(5).map(friend -> {
                        try {
                            Student student = friend.getSender();
                            FXMLLoader loader = Router.getInstance().getParentNode("friend-invitation");
                            AnchorPane pane = (AnchorPane) loader.load();
                            FriendInvitationController fr = loader.getController();
                            fr.getName().setText(student.getFamilyname() + " " + student.getFirstname());
                            Image image = new Image(convertBytesToInputStream(student.getProfileImageStream()));
                            fr.getProfileImage().setImage(image);
                            fr.getFormation().setText(student.getUniversity());
                            return pane;
                        } catch (Exception e) {
                            return new AnchorPane();
                        }
                    }).collect(Collectors.toList()));

            invitationPanelZone.getChildren().setAll(friends_invitation.stream().map(friend -> {
                try {
                    Student student = friend.getSender();
                    FXMLLoader loader = Router.getInstance().getParentNode("friend-invitation");
                    AnchorPane pane = (AnchorPane) loader.load();
                    FriendInvitationController fr = loader.getController();
                    fr.getName().setText(student.getFamilyname() + " " + student.getFirstname());
                    Image image = new Image(convertBytesToInputStream(student.getProfileImageStream()));
                    fr.getProfileImage().setImage(image);
                    fr.getFormation().setText(student.getUniversity());
                    return pane;
                } catch (Exception e) {
                    return new AnchorPane();
                }
            }).collect(Collectors.toList()));

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void initializeBtn(Button... btnsListf) {
        for (Button button : btnsListf) {
            button.setOnAction(event -> {
                for (ScrollPane entry : this.btnmapper.values()) {
                    entry.setVisible(false);
                }
                btnmapper.get(button).setVisible(true);
            });
        }
    }

    private static InputStream convertBytesToInputStream(byte[] byteArray) {
        return new ByteArrayInputStream(byteArray);
    }

    public static String durationOfInvitation(Timestamp timestamp) {
        Instant now = Instant.now();
        Instant then = timestamp.toInstant();
        Duration duration = Duration.between(then, now);

        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        long days = absSeconds / 86400;
        long months = days / 30;
        long hours = (absSeconds % 86400) / 3600;
        long minutes = (absSeconds % 3600) / 60;
        long secs = absSeconds % 60;

        if (months >= 12) {
            long years = months / 12;
            return years + " an" + (years == 1 ? "" : "s");
        } else if (months >= 1) {
            return months + " mois";
        } else if (days >= 1) {
            return days + " jour(s)" + (days == 1 ? "" : "s");
        } else if (hours >= 1) {
            return hours + " h";
        } else if (minutes >= 1) {
            return minutes + " min";
        } else {
            return secs + " s";
        }
    }
}
