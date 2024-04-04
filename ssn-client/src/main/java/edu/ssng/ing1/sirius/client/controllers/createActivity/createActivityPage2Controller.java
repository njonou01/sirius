package edu.ssng.ing1.sirius.client.controllers.createActivity;

import java.net.URL;
import java.util.ResourceBundle;

import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPoUp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

/**
 * createActivityPage2Controller
 */
public class createActivityPage2Controller implements Initializable {
    RouterPoUp router;

    @FXML
    TextField nbrParticipantTextField;

    @FXML
    TextField dateDeFInTextField;

    @FXML
    TextField dateDebutTextField;

    @FXML
    Label label;

    @FXML
    private Slider slider;

    @FXML
    private Label labelTexteField;

    @FXML
    Label labelCount;

    private int maxLength = 3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        router = RouterPoUp.getInstance();

        nbrParticipantTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > maxLength) {
                nbrParticipantTextField.setText(oldValue);

            }

            if (!newValue.matches("^([1-9]|[1-9][0-9]|100|101)$")) {

                nbrParticipantTextField.setStyle("-fx-background-color: #FFCCCC;");
                // nameActivityField.setStyle("-fx-text-fill: red;");
                label.setText("Valeur Max 100");
                // label.setStyle("-fx-text-fill: red;");
                // nbrParticipantTextField.setText(oldValue);
            } else {
                nbrParticipantTextField.setStyle("");
                label.setText("");
            }

        });

        nbrParticipantTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {

                labelCount.setOpacity(0.5);
                slider.setOpacity(0.5); 
            } else {
                labelCount.setOpacity(1.0);
                slider.setOpacity(1.0); 
            }
        });
        
        // Écouteur de focus pour le Slider
        slider.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                labelTexteField.setOpacity(0.5);
                nbrParticipantTextField.setOpacity(0.5); 
            } else {
                labelTexteField.setOpacity(1.0);
                nbrParticipantTextField.setOpacity(1.0); 
            }
        });

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            labelCount.setText(""+newValue.intValue());
        });

    }

    @FXML
    public void nextPage() {

        router.navigateTo("createActivityPage3");
        RouterPoUp.activite.setDatedebut(dateDebutTextField.getText());
        RouterPoUp.activite.setDatefin(dateDeFInTextField.getText());
        RouterPoUp.activite.setNbrparticipant(Integer.parseInt(nbrParticipantTextField.getText()));

    }

    @FXML
    public void closePage() {

        router.getStage().close();
        ;

    }

}