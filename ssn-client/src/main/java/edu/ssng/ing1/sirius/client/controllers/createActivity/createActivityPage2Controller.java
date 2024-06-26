package edu.ssng.ing1.sirius.client.controllers.createActivity;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ResourceBundle;

import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPopUp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

/**
 * createActivityPage2Controller
 */
public class createActivityPage2Controller implements Initializable {
    RouterPopUp router;

    @FXML
    TextField nbrParticipantTextField;

    @FXML
    TextField dateDeFInTextField;

    @FXML
    TextField dateDebutTextField;

    @FXML
    private Label label;

    @FXML
    private Slider slider;

    @FXML
    private Label labelTexteField;

    @FXML
    private Label labelCount;

    @FXML
    private DatePicker datePickerBegin;

    @FXML
    private DatePicker datePickerEnd;

    @FXML
    private ChoiceBox choiceConfidentSelection;

    private Integer numberChoice;

    private int maxLength = 3;

    private Object lastFocused = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        router = RouterPopUp.getInstance();

        choiceConfidentSelection.getItems().addAll(RouterPopUp.getConfi());
        choiceConfidentSelection.getSelectionModel().selectFirst();

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
                // label.setText("");
            }

        });

        nbrParticipantTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {

                if (lastFocused != nbrParticipantTextField) {
                    slider.setOpacity(0.5);
                    labelCount.setOpacity(0.5);
                    nbrParticipantTextField.setOpacity(1.0);
                    labelTexteField.setOpacity(1.0);
                    lastFocused = nbrParticipantTextField;
                }
            }

        });

        slider.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {

                if (lastFocused != slider) {
                    nbrParticipantTextField.setOpacity(0.5);
                    labelTexteField.setOpacity(0.5);
                    slider.setOpacity(1.0);
                    labelCount.setOpacity(1.0);
                    lastFocused = slider;
                }
            }
        });

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            labelCount.setText("" + newValue.intValue());
        });

        datePickerBegin.setOnAction(event -> {
            LocalDate localDate = datePickerBegin.getValue();
            if (localDate != null) {
                Timestamp timestamp = Timestamp.valueOf(localDate.atStartOfDay());
                System.out.println("Timestamp : " + timestamp);
                RouterPopUp.activite.setDatedebut("" + timestamp);
            }
        });

        datePickerEnd.setOnAction(event -> {
            LocalDate localDate = datePickerEnd.getValue();
            if (localDate != null) {
                Timestamp timestamp = Timestamp.valueOf(localDate.atStartOfDay());
                RouterPopUp.activite.setDatefin("" + timestamp);

            }
        });

        nbrParticipantTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                numberChoice = Integer.parseInt(newValue);
                slider.setValue(numberChoice.doubleValue());
            } catch (NumberFormatException e) {

            }
        });

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            numberChoice = newValue.intValue();
            nbrParticipantTextField.setText(String.valueOf(numberChoice));
        });

    }

    @FXML
    public void nextPage() {
        RouterPopUp.activite.setNbrparticipant(numberChoice);
        RouterPopUp.MinousProgress += 0.33;
        RouterPopUp.progressBar.setProgress(RouterPopUp.MinousProgress);

        router.navigateTo("createActivityPage3");

        System.out.println("Timestampuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu : " + RouterPopUp.activite
                + "uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");

    }

    @FXML
    public void closePage() {

        router.getStage().close();
        ;

    }

}