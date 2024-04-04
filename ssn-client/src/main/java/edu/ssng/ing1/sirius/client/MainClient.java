package edu.ssng.ing1.sirius.client;

import edu.ssng.ing1.sirius.client.router.Router;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage preloaderStage = new Stage();
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        Parent preloaderRoot = Router.getInstance().getParentNode("loading").load();
        Scene preloaderScene = new Scene(preloaderRoot);
        preloaderStage.setScene(preloaderScene);
        preloaderStage.show();

        PreloadService preloadService = new PreloadService();
        preloadService.setOnSucceeded(event -> {
            preloaderStage.close();
            try {
                Stage mainStage = new Stage();
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();
                

                mainStage.setX(bounds.getMinX());
                mainStage.setY(bounds.getMinY());
                mainStage.setWidth(bounds.getWidth());
                mainStage.setHeight(bounds.getHeight());
                Router.getInstance().setStage(mainStage);
                Router.getInstance().navigateTo("main");
                mainStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        preloadService.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class PreloadService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    simulateLongLoading();
                    return null;
                }
            };
        }

        private void simulateLongLoading() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
