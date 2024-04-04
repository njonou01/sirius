package edu.ssng.ing1.sirius.client.controllers.createActivity;

import java.net.URL;
import java.util.ResourceBundle;

import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPoUp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * createActivityPage2Controller
 */
public class createActivityPage2Controller  implements Initializable{
    RouterPoUp router;

     @FXML   
    TextField nbrParticipantTextField;

    @FXML
    TextField dateDeFInTextField;

    @FXML
    TextField dateDebutTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        router= RouterPoUp.getInstance();
        
    }

    @FXML
    public void nextPage(){

        router.navigateTo("createActivityPage3");
        RouterPoUp.activite.setDatedebut(dateDebutTextField.getText());
        RouterPoUp.activite.setDatefin(dateDeFInTextField.getText());
        RouterPoUp.activite.setNbrparticipant(Integer.parseInt(nbrParticipantTextField.getText()));

    }
    @FXML
    public void closePage(){

        router.getStage().close();;
        
    }

    
}