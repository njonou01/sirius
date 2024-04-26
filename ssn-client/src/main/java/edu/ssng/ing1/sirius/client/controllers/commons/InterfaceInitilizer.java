package edu.ssng.ing1.sirius.client.controllers.commons;

import java.io.IOException;

import edu.ssng.ing1.sirius.client.router.Router;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class InterfaceInitilizer {
    private static BorderPane friendPane;
    public static BorderPane getFriendPane() {
        return friendPane;
    }

    public static void initializeAll() {
        friendPane = initializeFriendPage();
    }

    private static BorderPane initializeFriendPage()  {
        BorderPane friendPane;
        try {
            FXMLLoader friendLoader = Router.getInstance().getParentNode("friend-page");
            friendPane = (BorderPane) friendLoader.load();
        } catch (IOException e) {
            friendPane = new BorderPane(new Label("Erreur"));
            e.printStackTrace();
        }
        return friendPane;
    }

}
