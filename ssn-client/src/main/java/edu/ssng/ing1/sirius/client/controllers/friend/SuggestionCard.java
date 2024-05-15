package edu.ssng.ing1.sirius.client.controllers.friend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.controllers.commons.CommonsClient;
import edu.ssng.ing1.sirius.client.controllers.commons.Initializer;
import edu.ssng.ing1.sirius.client.controllers.commons.UserInfo;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.requests.friend.FriendCommonRequest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class SuggestionCard extends AnchorPane {
    public SuggestionCard(Student friend) {
        try {
            FXMLLoader loader = Router.getInstance().getParentNode("suggestion-card");
            Parent root = (AnchorPane) loader.load();
            this.getChildren().setAll(root);

            SuggestionCardController controller = loader.getController();
            controller.getCloseBtn().setOnAction(e -> {
                removeMe();
            });
            controller.getFullName().setText(friend.getFamilyname() + " " + friend.getFirstname());
            controller.getSchool().setText(friend.getTraining_followed());
            controller.getImage().setImage(CommonsClient.getImage(friend.getProfileImageStream()));
            controller.getDemandFriendBtn().setOnAction(e -> {
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("receiver", friend.getId_student());
                map.put("sender", UserInfo.getUser().getId_student());
                try {
                    Map<String, String> response = FriendCommonRequest.askFriendship(map);
                    if (response.get("response").equals("success")) {
                        removeMe();
                        Initializer.getSuggestions().getStudents().remove(friend);
                        FriendUpdater.updateLimitedSuggestedFriend();
                        FriendUpdater.updateSuggestedFriend();
                    }
                } catch (IOException | InterruptedException e1) {
                    e1.printStackTrace();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeMe() {
        FlowPane parent = (FlowPane) this.getParent();
        parent.getChildren().remove(this);
    }

}
