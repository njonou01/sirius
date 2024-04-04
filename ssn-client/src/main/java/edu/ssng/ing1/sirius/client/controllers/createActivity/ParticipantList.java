package edu.ssng.ing1.sirius.client.controllers.createActivity;

import java.net.URL;
import java.util.ResourceBundle;

import edu.ssng.ing1.sirius.client.router.RouterPoUp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ParticipantList implements Initializable{

    RouterPoUp router;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        router=RouterPoUp.getInstance();
        
    }

    @FXML
    public void closePage(){

        router.navigateTo("activityCreated");
        
    }
    
}
