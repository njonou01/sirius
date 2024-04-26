package edu.ssng.ing1.sirius.client.controllers.commons;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class LoadingController implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private Label loading;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("media/images/ssn-logo.png");
        logo.setImage(new Image(inputStream));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> loading.setText("Loading")),
                new KeyFrame(Duration.seconds(0.25), e -> loading.setText("Loading.")),
                new KeyFrame(Duration.seconds(0.5), e -> loading.setText("Loading..")),
                new KeyFrame(Duration.seconds(1), e -> loading.setText("Loading...")),
                new KeyFrame(Duration.seconds(1.25), e -> loading.setText("Loading...."))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

}
