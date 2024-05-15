package edu.ssng.ing1.sirius.client.controllers.friend;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.ssng.ing1.sirius.business.dto.BeFriend;
import edu.ssng.ing1.sirius.business.dto.BeFriends;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.controllers.commons.CommonsClient;
import edu.ssng.ing1.sirius.client.controllers.commons.Initializer;
import edu.ssng.ing1.sirius.client.controllers.commons.UserInfo;
import edu.ssng.ing1.sirius.client.controllers.messaging.FriendBtn;
import edu.ssng.ing1.sirius.client.controllers.messaging.MessagingUpdater;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.requests.friend.FriendCommonRequest;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import edu.ssng.ing1.sirius.client.controllers.friend.FriendCardController;
public class FriendUpdater {
    private static FriendUpdater instance;
    private static FlowPane acceptedFriend;
    private static FlowPane demandedFriend;
    private static FlowPane suggestedFriend;
    private static FlowPane limitedDemandedFriend;
    private static FlowPane limitedSuggestedFriend;

    public static void renitialize() {
        instance = null;
    }


    private FriendUpdater(FlowPane acceptedFriend, FlowPane demandedFriend, FlowPane suggestedFriend,
            FlowPane limitedDemandedFriend,
            FlowPane limitedSuggestedFriend) {
        FriendUpdater.acceptedFriend = acceptedFriend;
        FriendUpdater.demandedFriend = demandedFriend;
        FriendUpdater.suggestedFriend = suggestedFriend;
        FriendUpdater.limitedDemandedFriend = limitedDemandedFriend;
        FriendUpdater.limitedSuggestedFriend = limitedSuggestedFriend;
    }

    public static FriendUpdater getInstance(
            FlowPane acceptedFriend,
            FlowPane demandedFriend,
            FlowPane suggestedFriend,
            FlowPane limitedDemandedFriend,
            FlowPane limitedSuggestedFriend) {
        if (instance == null) {
            instance = new FriendUpdater(acceptedFriend, demandedFriend, suggestedFriend, limitedDemandedFriend,
                    limitedSuggestedFriend);
            UserInfo.getInstance();

            System.out.println("FriendUpdater instance created");
        }
        return instance;
    }

    public static void updateAcceptedFriend() {
        acceptedFriend.getChildren().clear();
        List<AnchorPane> accepted = Initializer.getFriends().stream()
                .map(friend -> {
                    return buildFriendCard(friend.getSender());
                }).collect(Collectors.toList());
        FriendUpdater.acceptedFriend.getChildren().setAll(accepted);
    }

    public static void updateDemandedFriend() {
        demandedFriend.getChildren().clear();
        List<AnchorPane> demanded = Initializer.friendshipRequestReceiver().stream()
                .map(friend -> {
                    return buildFriendCard(friend.getSender());
                }).collect(Collectors.toList());
        FriendUpdater.demandedFriend.getChildren().setAll(demanded);
    }

    public static void updateSuggestedFriend() {
        suggestedFriend.getChildren().clear();
        List<AnchorPane> suggested = Initializer.getSuggestions().getStudents().stream()
                .map(friend -> {
                    return new SuggestionCard(friend);
                }).collect(Collectors.toList());
        FriendUpdater.suggestedFriend.getChildren().setAll(suggested);
    }

    public static void updateLimitedSuggestedFriend() {
        limitedSuggestedFriend.getChildren().clear();
        List<AnchorPane> suggested = Initializer.getSuggestions().getStudents().stream().limit(8).map(friend -> {
            return new SuggestionCard(friend);
        }).collect(Collectors.toList());
        FriendUpdater.limitedSuggestedFriend.getChildren().setAll(suggested);
    }

    public static void updateLimitedDemandedFriend() {
        limitedDemandedFriend.getChildren().clear();
        List<AnchorPane> demanded = Initializer.friendshipRequestReceiver().stream().limit(5).map(friend -> {
            return buildInvitation(friend.getSender());
        }).collect(Collectors.toList());
        FriendUpdater.limitedDemandedFriend.getChildren().setAll(demanded);
    }

    private static AnchorPane buildInvitation(Student sender) {
        try {
            FXMLLoader loader = Router.getInstance().getParentNode("friend-invitation");
            AnchorPane pane = (AnchorPane) loader.load();
            FriendInvitationController fr = loader.getController();
            Map<String, Integer> map = new LinkedHashMap<>();
            map.put("receiver", UserInfo.getUser().getId_student());
            map.put("sender", sender.getId_student());
            setIgnoreOnAction(fr.getIgnoreBtn(), demandedFriend, pane, map);
            setAcceptOnAction(fr.getAcceptBtn(), demandedFriend, pane, map);
            fr.getName().setText(sender.getFamilyname() + " " + sender.getFirstname());
            Image image = new Image(CommonsClient.convertBytesToInputStream(sender.getProfileImageStream()));
            fr.getProfileImage().setImage(image);
            fr.getFormation().setText(sender.getUniversity());
            return pane;
        } catch (Exception e) {
            return new AnchorPane();
        }
    }

    private static AnchorPane buildFriendCard(Student friend) {
        try {
            FXMLLoader loader = Router.getInstance().getParentNode("friend-card");
            AnchorPane pane = (AnchorPane) loader.load();
            FriendCardController fr = loader.getController();
            Image image = new Image(CommonsClient.convertBytesToInputStream(friend.getProfileImageStream()));
            fr.getName().setText(friend.getFamilyname() + " " + friend.getFirstname());
            fr.getProfileImage().setImage(image);
            fr.getFormation().setText(friend.getUniversity());
            return pane;
        } catch (Exception e) {
            return new AnchorPane();
        }
    }

    public static void setAcceptOnAction(Button acceptBtn, FlowPane parent, AnchorPane pane, Map<String, Integer> map) {
        acceptBtn.setOnAction(event -> {
            parent.getChildren().remove(pane);
            try {
                Map<String, String> result = FriendCommonRequest.becomeFriend(map);
                if (result.get("response").equals("success")) {
                    Initializer.getinvitations().getBefriends().forEach(f -> {
                        if (f.getSender().getId_student() == map.get("sender")
                                && f.getReceiver().getId_student() == map.get("receiver")) {
                            f.setStatus("accepted");
                        }
                    });
                    updateAcceptedFriend();
                    updateDemandedFriend();
                    updateLimitedDemandedFriend();
                    MessagingUpdater.updateStudentList();
                    System.out.println("Friendship request accepted");
                }

            } catch (Exception e) {

            }
        });
    }

    public static void setIgnoreOnAction(Button ignoreBtn, FlowPane parent, AnchorPane pane, Map<String, Integer> map) {
        ignoreBtn.setOnAction(event -> {
            parent.getChildren().remove(pane);
            try {
                Map<String, String> result = FriendCommonRequest.deleteInvitation(map);
                if (result.get("response").equals("success")) {
                    Initializer.getinvitations().getBefriends()
                            .removeIf(f -> f.getSender().getId_student() == map.get("sender")
                                    && f.getReceiver().getId_student() == map.get("receiver"));
                    updateDemandedFriend();
                    updateLimitedDemandedFriend();
                    System.out.println("Friendship request ignored");
                }
            } catch (Exception e) {
                System.out.println("ffffffffff rrrrrrrrrrrrr");
            }
        });
    }

}