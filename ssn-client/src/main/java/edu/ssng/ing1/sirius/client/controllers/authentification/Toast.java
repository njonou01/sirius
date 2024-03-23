package edu.ssng.ing1.sirius.client.controllers.authentification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import edu.ssng.ing1.sirius.client.router.Router;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Toast {

    private static Set<Stage> toastStageList = new CopyOnWriteArraySet<Stage>();
    private static final int TOAST_FADE_DURATION = 10000;

    public static void buildToast(String message) {
        try {

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.prefWidthProperty().bind(Router.getInstance().getStage().widthProperty());
            anchorPane.prefHeightProperty().bind(Router.getInstance().getStage().heightProperty());

            Stage owner = Router.getInstance().getStage();

            FXMLLoader toastLoader = Router.getInstance().getParentNode("toast");

            AnchorPane root = (AnchorPane) toastLoader.load();

            ToastController toastControl = toastLoader.getController();
            toastControl.setToastMessage(message);

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            Stage toastStage = new Stage();

            toastStage.initOwner(owner);
            toastStage.initStyle(StageStyle.UNDECORATED);

            toastStage.setOpacity(0.9);
            toastStage.setScene(scene);

            Bounds ownerBounds = owner.getScene().getRoot().getBoundsInLocal();
            double toastWidth = root.getWidth();
            double toastHeight = root.getHeight();
            double ownerX = ownerBounds.getMinX();
            double ownerY = ownerBounds.getMinY();
            double ownerWidth = ownerBounds.getWidth();
            double ownerHeight = ownerBounds.getHeight();
            double toastX = ownerX + ownerWidth - toastWidth;
            double toastY = (ownerY + ownerHeight - toastHeight);

            for (Stage stage : toastStageList) {
                toastY -= (stage.getHeight());
            }
            toastStage.setX(toastX);
            toastStage.setY(toastY);
            toastStageList.add(toastStage);

            Timeline timeline = new Timeline();
            KeyFrame key = new KeyFrame(Duration.millis(TOAST_FADE_DURATION),
                    new KeyValue(toastStage.opacityProperty(), 0));
            timeline.getKeyFrames().add(key);
            timeline.setOnFinished(event -> {
                Boolean test = false;
                Stage currenStage = null;
                for (Stage stage : toastStageList) {
                    if (test) {
                        stage.setY(stage.getY() + currenStage.getHeight());
                    }
                    if (stage == toastStage) {
                        toastStageList.remove(stage);
                        toastStage.close();
                        currenStage = stage;
                        test = true;
                    }
                }
                ;
            });

            toastStage.show();
            timeline.play();
        } catch (Exception e) {

        }
    }

}
