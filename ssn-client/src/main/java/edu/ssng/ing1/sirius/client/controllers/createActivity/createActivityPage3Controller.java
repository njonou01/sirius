package edu.ssng.ing1.sirius.client.controllers.createActivity;

import java.net.URL;
import java.util.ResourceBundle;

import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPoUp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * createActivityPage2Controller
 */
public class createActivityPage3Controller  implements Initializable{
    RouterPoUp router;

    @FXML
    TextArea LibeleTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        router= RouterPoUp.getInstance();
        
    }

    @FXML
    public void nextPage(){

        router.navigateTo("createActivityPage4");
        RouterPoUp.activite.setLibelle(LibeleTextField.getText());

    }
    @FXML
    public void closePage(){

        router.getStage().close();;
        
    }

    
}