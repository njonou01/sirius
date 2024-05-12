package edu.ssng.ing1.sirius.client.controllers.commons;

import java.io.IOException;

import org.controlsfx.tools.Borders.Border;

import edu.ssng.ing1.sirius.client.router.Router;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class Initializer {
    public static BorderPane initInvitationPage() {
        FXMLLoader fxml = Router.getInstance().getParentNode("friend-page");
        System.out.println(fxml);
        try {
            return (BorderPane) fxml.load();
        } catch (IOException e) {
            return null;
        }
    }
    public static BorderPane initMessagingPage() {
        FXMLLoader fxml = Router.getInstance().getParentNode("messaging");
        try {
           BorderPane pane = (BorderPane) fxml.load();
            fxml.getController();

            return pane;
        } catch (IOException e) {
            return null;
        }
    }
}
