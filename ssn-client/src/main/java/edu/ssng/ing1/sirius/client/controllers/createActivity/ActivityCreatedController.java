package edu.ssng.ing1.sirius.client.controllers.createActivity;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ResourceBundle;

import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

// import edu.ssng.ing1.sirius.MainSelectClient;
import edu.ssng.ing1.sirius.business.dto.Activite;
import edu.ssng.ing1.sirius.business.dto.Activites;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPopUp;
import edu.ssng.ing1.sirius.requests.activities.SelectActivityQuery;
import edu.ssng.ing1.sirius.requests.activities.SelectMyActivityQuery;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ActivityCreatedController implements Initializable {

    @FXML
    VBox parentVBox;

    Router router;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        router = Router.getInstance();
        Student student = new Student();
        student.setId_student(3);

        Activites activites = new Activites();
        try {
            activites = SelectActivityQuery.SelectActivite();
        } catch (IOException | InterruptedException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (final Activite activite : activites.getActivites()) {

            oneActivity(activite);

            // System.out.println(activite.getNom_interet_activite() +
            // activite.getDatecreation()
            // + activite.getNomCreateur() + activite.getNbrparticipant());
        }

    }

    @FXML
    public void addActivity() {

        System.out.println("OneActivitycreated");

    }

    public void oneActivity(Activite activite) {

        ImageView imageView = new ImageView();
        // imageView.setImage();
        imageView.setFitWidth(102);

        Label labelNomActivite = new Label("Nom Activite : " + activite.getNom_interet_activite());
        Label labelCreateur = new Label("Createur :" + activite.getNomCreateur());

        Label labelDebut = new Label("Debut : " + activite.getDatedebut());
        Label labelFin = new Label("Fin : " + activite.getDatefin());

        Button buttonParticipants = new Button("Participants :" + activite.getNbrparticipant());
        buttonParticipants.setOnAction(event -> seePart());

        FontIcon discussIcon = new FontIcon(FontAwesomeRegular.COMMENT_DOTS);
        discussIcon.setIconColor(Color.valueOf("#2361a6"));
        discussIcon.setIconSize(25);

        FontIcon quitIcon = new FontIcon(FontAwesomeSolid.SIGN_OUT_ALT);
        quitIcon.setIconColor(Color.valueOf("#b03a65"));
        quitIcon.setIconSize(25);

        ProgressIndicator progressIndicator = new ProgressIndicator(0.0);
        progressIndicator.setMinWidth(85);
        progressIndicator.setMinHeight(100.0);
        progressIndicator.setMinWidth(85.0);
        progressIndicator.setPrefHeight(41.0);
        progressIndicator.setPrefWidth(126.0);

        HBox hBoxLabels = new HBox(40);
        hBoxLabels.getChildren().addAll(labelDebut, labelFin);

        HBox hBoxButtons = new HBox(50);
        hBoxButtons.getChildren().addAll(buttonParticipants, quitIcon, discussIcon);
        buttonParticipants.getStyleClass().add("btn");
        VBox vBoxLabelsAndButtons = new VBox(10);
        vBoxLabelsAndButtons.getChildren().addAll(labelNomActivite, labelCreateur, hBoxLabels, hBoxButtons);
        VBox.setMargin(vBoxLabelsAndButtons, new Insets(0, 0, 0, 20));

        HBox hBox = new HBox();
        hBox.getStyleClass().add("centerBox");

        hBox.getChildren().addAll(imageView, vBoxLabelsAndButtons, progressIndicator);
        HBox.setMargin(hBox, new Insets(10, 0, 0, 0));
        // setPadding(hBox, new Insets(10, 0, 0, 0));
        parentVBox.getChildren().add(hBox);

    }
    // public void oneActivity2(Activite activite) {

    // // Set preferred size and style class
    // setPrefHeight(127.0);
    // setPrefWidth(730.0);
    // getStyleClass().add("activitycreatedBG");

    // // Create ImageView (replace with your image path)
    // ImageView imageView = new ImageView();
    // imageView.setFitHeight(107.0);
    // imageView.setFitWidth(102.0);
    // imageView.setPickOnBounds(true);
    // imageView.setPreserveRatio(true);

    // // Create VBox for activity details
    // VBox detailsVBox = new VBox();
    // detailsVBox.setPrefHeight(107.0);
    // detailsVBox.setPrefWidth(454.0);
    // detailsVBox.setSpacing(10.0);
    // setPadding(new Insets(10, 10, 10, 10));

    // // Create Labels for activity name and creator
    // Label activityNameLabel = new Label("Nom Activité : échec");
    // activityNameLabel.setPrefHeight(17.0);
    // activityNameLabel.setPrefWidth(369.0);

    // Label creatorLabel = new Label("Createur : Vous");
    // creatorLabel.setPrefHeight(17.0);
    // creatorLabel.setPrefWidth(370.0);

    // // Create HBox for start and end time
    // HBox timeHBox = new HBox();
    // timeHBox.setPrefHeight(57.0);
    // timeHBox.setPrefWidth(323.0);
    // timeHBox.setSpacing(40.0);

    // Label startTimeLabel = new Label("Début : 122");
    // startTimeLabel.setPrefHeight(17.0);
    // startTimeLabel.setPrefWidth(173.0);

    // Label endTimeLabel = new Label("Fin : 133");
    // endTimeLabel.setPrefHeight(17.0);
    // endTimeLabel.setPrefWidth(156.0);

    // timeHBox.getChildren().addAll(startTimeLabel, endTimeLabel);

    // HBox buttonHBox = new HBox();
    // buttonHBox.setPrefHeight(100.0);
    // buttonHBox.setPrefWidth(200.0);
    // buttonHBox.setSpacing(50.0);

    // Button participantsButton = new Button("Participants");
    // participantsButton.setOnAction(event -> seePart());
    // participantsButton.getStyleClass().add("btn");

    // FontIcon discussIcon = new FontIcon("far-comment-dots",
    // Color.valueOf("#2361a6"), 25);
    // discussIcon.setOnMouseClicked(event -> goToDiscution());

    // FontIcon quitIcon = new FontIcon("fas-sign-out-alt",
    // Color.valueOf("#b03a65"), 25);
    // quitIcon.setOnMouseClicked(event -> quitActivity());

    // buttonHBox.getChildren().addAll(participantsButton, discussIcon, quitIcon);

    // // Create ProgressIndicator
    // ProgressIndicator progressIndicator = new ProgressIndicator(0.0);
    // progressIndicator.setMinHeight(100.0);
    // progressIndicator.setMinWidth(85.0);
    // progressIndicator.setPrefHeight(41.0);
    // progressIndicator.setPrefWidth(126.0);

    // // Add children to VBox and HBox
    // detailsVBox.getChildren().addAll(activityNameLabel, creatorLabel, timeHBox,
    // buttonHBox);
    // getChildren().addAll(imageView, detailsVBox, progressIndicator);

    // // Set margins for VBox and ProgressIndicator within HBox
    // setMargin(detailsVBox, new Insets(20, 0, 0, 20));
    // setMargin(progressIndicator, new Insets(10, 0, 0, 0));

    // }

    @FXML
    public void goToDiscution() {

    }

    @FXML
    public void backBTN() {
        router.navigateTo("main");
    }

    @FXML
    public void quitActivity() {
        
    }

    private void progresseIndicator() {
        // Timeline timeline = new Timeline(
        // new KeyFrame((Duration.ofMillis(100), event -> {

        // for (ProgressBar progressBar : progressBars) {
        // double progress = progressBar.getProgress() + PROGRESS_INCREMENT;
        // if (progress > 1.0) {
        // progress = 0.0;
        // }
        // progressBar.setProgress(progress);
        // }
        // })
        // );
        // timeline.setCycleCount(Timeline.INDEFINITE);
        // timeline.play();

    }

    @FXML
    public void closePage() {

        router.getStage().close();

    }

    @FXML
    public void seePart() {
        router.navigateTo("listeParticipant");
    }

    @FXML
    public void openChat() {

    }

    // ImageView imageView = new ImageView();
    // imageView.setFitHeight(107.0);
    // imageView.setFitWidth(102.0);

    // Label label1 = new Label("Nom Activité : échec");
    // Label label2 = new Label("Createur : Vous");
    // Label label3 = new Label("Début : 122");
    // Label label4 = new Label("Fin : 133");

    // Button button1 = new Button("Participants");
    // Button button2 = new Button();
    // button2.getStyleClass().add("btn");

    // ProgressIndicator progressIndicator = new ProgressIndicator(0.0);
    // progressIndicator.setMinHeight(100.0);
    // progressIndicator.setMinWidth(85.0);
    // progressIndicator.setPrefHeight(41.0);
    // progressIndicator.setPrefWidth(126.0);

    // HBox hbox1 = new HBox(label3, label4);
    // hbox1.setSpacing(40.0);
    // HBox hbox2 = new HBox(button1, button2);
    // hbox2.setSpacing(50.0);

    // VBox vbox = new VBox(label1, label2, hbox1, hbox2, progressIndicator);
    // vbox.setPrefHeight(107.0);
    // vbox.setPrefWidth(454.0);
    // vbox.setSpacing(10.0);
    // vbox.setPadding(new Insets(10.0));

    // HBox hbox = new HBox(imageView, vbox);
    // hbox.setPrefHeight(127.0);
    // hbox.setPrefWidth(730.0);
    // hbox.getStyleClass().add("activitycreatedBG");
    // hbox.setMargin(vbox, new Insets(0, 0, 0, 20.0));
}
