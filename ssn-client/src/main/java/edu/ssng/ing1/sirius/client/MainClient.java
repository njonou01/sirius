package edu.ssng.ing1.sirius.client;

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

        Router router = Router.getInstance();
        router.setStage(stage);

        router.navigateTo("authentification");

        System.out.println("----main---> " + router.getStage());


    }

    public static void main(String[] args) {
            launch(args);
    }

}
