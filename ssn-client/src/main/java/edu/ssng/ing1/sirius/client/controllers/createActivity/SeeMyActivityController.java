package edu.ssng.ing1.sirius.client.controllers.createActivity;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ResourceBundle;

// import edu.ssng.ing1.sirius.MainSelectClient;
import edu.ssng.ing1.sirius.business.dto.Activite;
import edu.ssng.ing1.sirius.business.dto.Activites;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPopUp;
import edu.ssng.ing1.sirius.requests.activities.SelectActivityQuery;
import edu.ssng.ing1.sirius.requests.activities.SelectMyActivityQuery;
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

public class SeeMyActivityController implements Initializable{







    @FXML
    VBox parentVBox;

    Router router;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // router = Router.getInstance();
        Student student= new Student();
        student.setId_student(3);

        Activites activites = new Activites();
        try {
            activites = SelectMyActivityQuery.SelectActivite(student);
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

            oneActivity();

            // System.out.println(activite.getNom_interet_activite() + activite.getDatecreation()
            //         + activite.getNomCreateur() + activite.getNbrparticipant());
        }

    }

    @FXML
    public void addActivity() {

        System.out.println("OneActivitycreated");

    }

    public void oneActivity() {

        // Création des éléments
        ImageView imageView = new ImageView();
        // imageView.setImage();
        imageView.setFitWidth(102);

        Label labelNomActivite = new Label("Nom Activité : échec");
        Label labelCreateur = new Label("Créateur : Vous");

        Label labelDebut = new Label("Début : 122");
        Label labelFin = new Label("Fin : 133");

        Button buttonParticipants = new Button("Participants");
        buttonParticipants.setOnAction(event -> seePart());

        Button buttonDiscutez = new Button("Discutez");
        buttonDiscutez.setOnAction(event -> openChat());

        ProgressIndicator progressIndicator = new ProgressIndicator(0.0);
        progressIndicator.setMinWidth(85);

        // Création des conteneurs
        HBox hBoxLabels = new HBox(40);
        hBoxLabels.getChildren().addAll(labelDebut, labelFin);

        HBox hBoxButtons = new HBox(50);
        hBoxButtons.getChildren().addAll(buttonParticipants, buttonDiscutez);

        VBox vBoxLabelsAndButtons = new VBox(10);
        vBoxLabelsAndButtons.getChildren().addAll(labelNomActivite, labelCreateur, hBoxLabels, hBoxButtons);
        VBox.setMargin(vBoxLabelsAndButtons, new Insets(0, 0, 0, 20));

        HBox hBox = new HBox();
        hBox.getChildren().addAll(imageView, vBoxLabelsAndButtons, progressIndicator);
        HBox.setMargin(hBox, new Insets(10, 0, 0, 0));
        parentVBox.getChildren().add(hBox);

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



    
}
