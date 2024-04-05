package edu.ssng.ing1.sirius.client.controllers.createActivity;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ResourceBundle;

// import edu.ssng.ing1.sirius.MainSelectClient;
import edu.ssng.ing1.sirius.business.dto.Activite;
import edu.ssng.ing1.sirius.business.dto.Activites;
import edu.ssng.ing1.sirius.client.requests.activities.SelectActivityQuery;
import edu.ssng.ing1.sirius.client.router.RouterPoUp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ActivityCreatedController implements Initializable {

    @FXML
    VBox parentVBox;
    RouterPoUp router;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        router= RouterPoUp.getInstance();

        Activites activites = new Activites();
        try {
            activites= SelectActivityQuery.SelectActivite();
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
           
           
            oneActivity(activite.getNom_interet_activite(), activite.getDatecreation(),activite.getNomCreateur(),activite.getNbrparticipant());
        
            System.out.println(activite.getNom_interet_activite()+ activite.getDatecreation()+activite.getNomCreateur()+activite.getNbrparticipant());
        }


    }

    @FXML
    public void addActivity() {



    
        System.out.println("OneActivitycreated");
        

       
    }

    public void oneActivity(String nomActivity, String DateP, String nom, int nbrP){

        VBox newVBox = new VBox();
        newVBox.setPrefHeight(200.0);
        newVBox.setPrefWidth(100.0);

        HBox hbox = new HBox();
        hbox.setPrefHeight(100.0);
        hbox.setPrefWidth(200.0);
        hbox.setSpacing(350.0);

        VBox labelVBox = new VBox();
        Label label1 = new Label("Activité : "+ nomActivity);
        Label label2 = new Label("Publié le : "+DateP);
        Label label3 = new Label("Par : "+ nom);
        labelVBox.getChildren().addAll(label1, label2, label3);

        hbox.getChildren().addAll(labelVBox, new Button("Manquant :"+ nbrP));
        hbox.setPadding(new javafx.geometry.Insets(0, 0, 0, 25.0)); 

        TextArea textArea = new TextArea();
        textArea.setPrefHeight(200.0);
        textArea.setPrefWidth(200.0);

        HBox buttonHBox = new HBox();
        buttonHBox.setPrefHeight(100.0);
        buttonHBox.setPrefWidth(200.0);
        buttonHBox.setSpacing(15.0);
        buttonHBox.getChildren().addAll(new Button("Voir les participants"), new Button("Ne plus Participer"));

        newVBox.getChildren().addAll(hbox, textArea, buttonHBox);

        parentVBox.getChildren().add(newVBox);

    }

    @FXML
    public void closePage(){

        router.getStage().close();
        
    }

    @FXML
    public void seePart(){
        router.navigateTo("listeParticipant");
    }
    
    @FXML
    public void openChat(){

    }

}
