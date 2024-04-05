package edu.ssng.ing1.sirius.client;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.prefs.Preferences;

import edu.ssng.ing1.sirius.client.controllers.core.InterfaceInitilizer;
import edu.ssng.ing1.sirius.client.controllers.core.PreloadRequest;
import edu.ssng.ing1.sirius.client.router.Router;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainClient extends Application {

    @Override
    public void start(Stage mainStage) throws Exception {
        String ssnemailpref = "SSN_USER_EMAIL";
        Preferences prefs = Preferences.userRoot().node(MainClient.class.getName());
        String userEmail = prefs.get(ssnemailpref, null);

        Router router = Router.getInstance();
        router.setStage(mainStage);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        mainStage.setX(bounds.getMinX());
        mainStage.setY(bounds.getMinY());
        mainStage.setWidth(bounds.getWidth());
        mainStage.setHeight(bounds.getHeight());

        if (userEmail == null) {
            Router.getInstance().navigateTo("authentification");
        } else {
           Preload preloadMain = new Preload(() -> { 
                PreloadRequest.getInstance();
                InterfaceInitilizer.initializeAll();
                return null;
            });
            preloadMain.setOnSucceeded(() -> {
                try {
                    Router.getInstance().navigateTo("main");
                    mainStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
