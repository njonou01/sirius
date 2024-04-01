package edu.ssng.ing1.sirius.client;

import java.util.prefs.Preferences;

import edu.ssng.ing1.sirius.client.router.Router;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainClient extends Application {
    @Override
    public void start(Stage stage) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        String ssnemailpref = "SSN_USER_EMAIL";
        Preferences prefs = Preferences.userRoot().node(MainClient.class.getName());
        String userEmail = prefs.get(ssnemailpref, null);

        Router router = Router.getInstance();
        router.setStage(stage);
        if (userEmail == null) {
            Router.getInstance().navigateTo("authentification");
        } else {
            Router.getInstance().navigateTo("main");
        }
        System.out.println("----main---> " + router.getStage());

    }

    public static void main(String[] args) {
        launch(args);
    }

}
