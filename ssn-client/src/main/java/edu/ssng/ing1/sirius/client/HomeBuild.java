package edu.ssng.ing1.sirius.client;

import java.io.IOException;

import edu.ssng.ing1.sirius.client.router.Router;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HomeBuild {
    public HomeBuild() {
        Stage preloadStage = new PreloadUI();
        MainPreload MainBackgroundService = new MainPreload();
        MainBackgroundService.setOnSucceeded(event -> {
            preloadStage.close();
            
            System.out.println("****************************************************");
            System.out.println(Router.getInstance().getStage());
            System.out.println("****************************************************");;
            Router.getInstance().navigateTo("main");
        });
        MainBackgroundService.start();
    }

    private static class MainPreload extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    
                    return null;
                }
            };
        }
    }

    private class PreloadUI extends Stage {
        public PreloadUI() {
            FXMLLoader fxml = Router.getInstance().getParentNode("loading");
            this.initStyle(StageStyle.UNDECORATED);
            Parent Parent;
            try {
                Parent = fxml.load();
                this.setScene(new Scene(Parent));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.show();
        }

    }

}