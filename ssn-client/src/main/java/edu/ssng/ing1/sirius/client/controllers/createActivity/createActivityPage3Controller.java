package edu.ssng.ing1.sirius.client.controllers.createActivity;

import java.net.URL;
import java.util.ResourceBundle;

import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPoUp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * createActivityPage2Controller
 */
public class createActivityPage3Controller  implements Initializable{
    RouterPoUp router;

    @FXML
    TextArea LibeleTextField;

    private int maxLength = 20;

    @FXML
    private Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        router= RouterPoUp.getInstance();

        LibeleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > maxLength) {
                LibeleTextField.setText(oldValue);
            }

            if (!newValue.matches("[a-zA-Z0-9]*")) {
                LibeleTextField.setStyle("-fx-background-color: #FFCCCC;");
                // nameActivityField.setStyle("-fx-text-fill: red;");
                // label.setText("Les caractères spéciaux ne sont pas autorisés");
                // label.setStyle("-fx-text-fill: red;");
            } else {
                LibeleTextField.setStyle("");
                // label.setText("");
            }

        });
        
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