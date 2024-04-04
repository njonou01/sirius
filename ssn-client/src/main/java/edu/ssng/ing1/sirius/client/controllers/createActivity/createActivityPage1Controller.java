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
public class createActivityPage1Controller  implements Initializable{
    RouterPoUp router;

    @FXML
    TextField categorieActivityField;

    @FXML
    TextField nameActivityField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        router= RouterPoUp.getInstance();
        
    }

    @FXML
    public void nextPage(){

        router.navigateTo("createActivityPage2");
        RouterPoUp.activite.setCategorie(categorieActivityField.getText());
        RouterPoUp.activite.setNom_interet_activite(nameActivityField.getText());;
        

    }
    
    @FXML
    public void closePage(){

        router.getStage().close();
        
    }
    
}