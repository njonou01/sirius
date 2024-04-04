package edu.ssng.ing1.sirius.client.controllers.createActivity;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import edu.ssng.ing1.sirius.client.controllers.authentification.Rules;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPoUp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/**
 * createActivityPage2Controller
 */
public class createActivityPage1Controller implements Initializable {
    private RouterPoUp router;

    @FXML
    private TextField categorieActivityField;

    @FXML
    private TextField nameActivityField;

    @FXML
    private Label label;

    private UnaryOperator<TextFormatter.Change> filter;
    private int maxLength = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        router = RouterPoUp.getInstance();
        nameActivityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > maxLength) {
                nameActivityField.setText(oldValue);
            }

            if (!newValue.matches("[a-zA-Z0-9]*")) {
                nameActivityField.setStyle("-fx-background-color: #FFCCCC;");
                // nameActivityField.setStyle("-fx-text-fill: red;");
                label.setText("Les caractères spéciaux ne sont pas autorisés");
                // label.setStyle("-fx-text-fill: red;");
            } else {
                nameActivityField.setStyle(""); 
                label.setText("");
            }

        });

    }

    @FXML
    public void nextPage() {

        router.navigateTo("createActivityPage2");
        if(nameActivityField.getText().matches("[a-zA-Z0-9]*")){
            RouterPoUp.activite.setCategorie(categorieActivityField.getText());
            RouterPoUp.activite.setNom_interet_activite(nameActivityField.getText());

        }
        
        

    }

    @FXML
    public void closePage() {

        router.getStage().close();

    }

}