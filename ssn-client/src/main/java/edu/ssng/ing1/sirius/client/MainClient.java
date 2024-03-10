package edu.ssng.ing1.sirius.client;

import java.io.IOException;

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
        try {
            System.out.println("----------------------------------");
            System.out.println(router.getStage().getScene());
            System.out.println("----------------------------------");

            router.navigateTo("main");
        } catch (Exception e) {
            e.printStackTrace();
            stage.close();
        }
    }


    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
