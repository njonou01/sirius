package edu.ssng.ing1.sirius.client.controllers.authentification;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;

import edu.ssng.ing1.sirius.business.dto.City;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.requests.authentification.CommonRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private TextField phoneNumber;

    @FXML
    private TextField zipField;

    @FXML
    private ComboBox<String> sexeField;

    public Button getNextBtn() {
        return nextBtn;
    }

    protected Boolean handleContinious() {
        Boolean bool1 = manageFirstName();
        Boolean bool2 = manageZipCode();
        Boolean bool3 = manageDate();
        Boolean bool4 = manageAddress();
        Boolean bool5 = manageSexe();
        return (bool1 && bool2 && bool3 && bool4 && bool5);
    }

    private Boolean manageFirstName() {
        if (!Rules.isValidName(lastnameField.getText())) {
            validateField(lastnameField, Rules::isValidName);
        }
        return Rules.isValidName(lastnameField.getText());
    }

    private Boolean manageDate() {
        System.out.println(birthdayField.getValue() == null);
        if (birthdayField.getValue() == null) {
            validateField(birthdayField, (date) -> !(date == null));
        }
        return !(birthdayField.getValue() == null);
    }

    private Boolean manageAddress() {
        if (addresField.getText() == null || addresField.getText().isBlank()) {
            validateField(addresField, (addres) -> !(addres == null || addres.isBlank()));
        }
        return !(addresField.getText() == null || addresField.getText().isBlank());
    }

    private Boolean manageSexe() {
        System.out.println();
        if (sexeField.getSelectionModel().getSelectedItem() == null) {
            validateField(sexeField, (sex) -> !(sex == null));
        }
        return !(sexeField.getSelectionModel().getSelectedItem() == null);
    }

    private Boolean manageZipCode() {
        if (!Rules.isValidFrenchZip(zipField.getText())) {
            validateField(zipField, Rules::isValidZip);
        }
        return Rules.isValidFrenchZip(zipField.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        birthdayField.setEditable(false);
        sexeField.getItems().addAll("Autre", "M", "F");

        addresField.setOnKeyReleased(event -> {
            addresField.setText(null);
        });
        addresField.setOnKeyTyped(event -> {
            addresField.setText(null);
        });
        try {
            Map<Integer, City> cities = CommonRequest.selectCities().toMap();
            zipField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    try {
                        String city = cities.get(Integer.parseInt(zipField.getText())).getCity_name();
                        addresField.setText(city);
                    } catch (Exception e) {
                        addresField.setText("");
                    }
                }
            });
        } catch (NullPointerException | IOException | InterruptedException e) {

        }
    }

    protected void setStudentData(Student student) {
        try {
            student.setBithday(Date.valueOf(birthdayField.getValue()));
        } catch (Exception e) {
        }
        student.setFirstname(firstNameField.getText());
        student.setFamilyname(lastnameField.getText());
        student.setAdress(addresField.getText());
        try {
            student.setZipcode(Integer.parseInt(zipField.getText()));
        } catch (Exception e) {
        }
        student.gender(sexeField.getSelectionModel().getSelectedItem());
        student.setPhoneNumber(phoneNumber.getText());

    }

    protected void renitialize() {
        addresField.clear();
        birthdayField.setValue(null);
        birthdayField.getStyleClass().remove("errortextfield");
        firstNameField.clear();
        lastnameField.clear();
        zipField.clear();
        sexeField.setValue(null);
    }

    private void validateField(TextField field, Function<String, Boolean> validator) {
        field.getStyleClass().add("errortextfield");
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            field.getStyleClass().removeAll(validator.apply(newValue)
                    ? "errortextfield"
                    : "");
            field.getStyleClass().add(!validator.apply(newValue)
                    ? "errortextfield"
                    : "");
        });
    }

    private void validateField(ComboBox<String> field, Function<String, Boolean> validator) {
        field.getStyleClass().add("errortextfield");
        field.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            field.getStyleClass().removeAll(validator.apply(newValue)
                    ? "errortextfield"
                    : "");
            field.getStyleClass().add(!validator.apply(newValue)
                    ? "errortextfield"
                    : "");
        });

    }

    private void validateField(DatePicker field, Function<String, Boolean> validator) {
        field.getStyleClass().add("errortextfield");
        field.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            field.getStyleClass().removeAll(validator.apply(newValue)
                    ? "errortextfield"
                    : "");
            field.getStyleClass().add(!validator.apply(newValue)
                    ? "errortextfield"
                    : "");
        });
    }

}
