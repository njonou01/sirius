package edu.ssng.ing1.sirius.client.controllers.authentification;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import edu.ssng.ing1.sirius.business.dto.City;
import edu.ssng.ing1.sirius.requests.authentification.CommonRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class SignUpStudentInfoController implements Initializable {
    @FXML
    private TextField addresField;

    @FXML
    private DatePicker birthdayField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private Button nextBtn;

    @FXML
    private TextField zipField;

    public Button getNextBtn() {
        return nextBtn;
    }

    protected Boolean handleContinious() {
        Boolean bool1 = manageFirstName();
        Boolean bool2 = manageZipCode();
        Boolean bool3 = manageDate();
        return (bool1 && bool2 && bool3);
    }

    private Boolean manageFirstName() {
        if (!Rules.isValidName(lastnameField.getText())) {
            lastnameField.getStyleClass().add("errortextfield");
            lastnameField.textProperty().addListener((observable, oldvalue, newvalue) -> {
                lastnameField.getStyleClass().removeAll(Rules.isValidName(newvalue)
                        ? "errortextfield"
                        : "");
                lastnameField.getStyleClass().add(!Rules.isValidName(newvalue)
                        ? "errortextfield"
                        : "");
            });
        }
        return Rules.isValidName(lastnameField.getText());
    }

    private Boolean manageDate() {
        if (!Rules.isValidDate(birthdayField.getEditor().getText())) {
            birthdayField.getEditor().getStyleClass().add("errortextfield");
            birthdayField.getEditor().textProperty().addListener((observable, oldvalue, newvalue) -> {
                birthdayField.getEditor().getStyleClass().removeAll(Rules.isValidDate(newvalue)
                        ? "errortextfield"
                        : "");
                birthdayField.getEditor().getStyleClass().add(!Rules.isValidDate(newvalue)
                        ? "errortextfield"
                        : "");
            });
        }
        return Rules.isValidDate(birthdayField.getEditor().getText());
    }

    private Boolean manageZipCode() {
        if (!Rules.isValidFrenchZip(zipField.getText())) {
            zipField.getStyleClass().add("errortextfield");
            zipField.textProperty().addListener((observable, oldvalue, newvalue) -> {
                zipField.getStyleClass().removeAll(Rules.isValidFrenchZip(newvalue)
                        ? "errortextfield"
                        : "");
                zipField.getStyleClass().add(!Rules.isValidFrenchZip(newvalue)
                        ? "errortextfield"
                        : "");
            });
        }
        return Rules.isValidFrenchZip(zipField.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addresField.setOnKeyReleased(event -> {
            addresField.setText(null);
        });
        addresField.setOnKeyTyped(event -> {
            addresField.setText(null);
        });
        try {
            Map<Integer,City> cities =  CommonRequest.selectCities().toMap();
            zipField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    String city = cities.get(Integer.parseInt(zipField.getText())).getCity_name();
                    addresField.setText(city);
                }
            });
        } catch (NullPointerException | IOException | InterruptedException e) {
            
        }
    }

}
