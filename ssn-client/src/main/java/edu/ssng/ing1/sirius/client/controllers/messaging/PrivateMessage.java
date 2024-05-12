package edu.ssng.ing1.sirius.client.controllers.messaging;

import java.io.IOException;

import edu.ssng.ing1.sirius.client.router.Router;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

public class PrivateMessage extends HBox {
    public PrivateMessage(MessageType type) {
        super();
        String whichMessage = type == MessageType.ME ? "my-messageUI" : "friend-messageUI";
        try {
            FXMLLoader loader = Router.getInstance().getParentNode(whichMessage);
            Parent root = loader.load();
            this.getChildren().setAll(root);
        } catch (IOException e) {

        }
    }
    static public enum MessageType {
        ME,
        FRIEND_MESSAGE
    }
}
