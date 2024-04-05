package edu.ssng.ing1.sirius.client;

import java.util.concurrent.Callable;

import edu.ssng.ing1.sirius.client.router.Router;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Preload {
    private PreloadService preloadService;
    private Stage preloaderStage;

    public Preload(Callable<Void> executequery) {
        try {
            this.preloaderStage = new Stage();
            preloaderStage.initStyle(StageStyle.UNDECORATED);
            Parent preloaderRoot;
            preloaderRoot = Router.getInstance().getParentNode("loading").load();
            Scene preloaderScene = new Scene(preloaderRoot);
            preloaderStage.setScene(preloaderScene);
            preloaderStage.show();
            this.preloadService = new PreloadService(executequery);
        } catch (Exception e) {

        }

    }

    public void setOnSucceeded(Callable<Void> function) {
        preloadService.setOnSucceeded(event -> {
            preloaderStage.close();
            try {
                function.call();
            } catch (Exception e) {

            }
        });
        this.preloadService.start();
    }

}
