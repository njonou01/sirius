package edu.ssng.ing1.sirius.client.controllers.authentification;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SignUpSchoolController implements Initializable {

    @FXML
    private ComboBox<Integer> endCombobox;

    @FXML
    private Button nextBtn;

    @FXML
    private ComboBox<String> schoolCombobox;

    @FXML
    private ComboBox<Integer> startCombobox;

    @FXML
    private CheckBox studyCurentlyCheck;

    @FXML
    private TextField textFieldSchool;

    public Button getNextBtn() {
        return nextBtn;
    }

    private String data[] = { "Apple", "Banana", "Orange", "Peach", "Grapes", "Pineapple" };

    private ObservableList<String> schoolList = FXCollections.observableArrayList(
            data);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studyCurentlyCheck.setOnAction(event -> {
            endCombobox.setDisable(studyCurentlyCheck.isSelected());
        });
        initStartCombobox(30, 0);
        textFieldSchool.textProperty().addListener((observable, oldValue, newValue) -> {
            schoolCombobox.hide();
            schoolCombobox.setItems(filterItems(newValue));
            schoolCombobox.show();
        });
        schoolCombobox.setOnAction(event -> {
            String selected = schoolCombobox.getSelectionModel().getSelectedItem();
            if (selected != null) {
                textFieldSchool.setText(selected);
                schoolCombobox.hide();
            }
        });

    }

    private void initStartCombobox(int begin, int end) {
        int currentYear = LocalDate.now().getYear();
        for (int year = currentYear - begin; year < currentYear + 1 + end; year++) {
            startCombobox.getItems().add(year);
        }
    }

    private ObservableList<String> filterItems(String filterText) {
        ObservableList<String> filteredList = FXCollections.observableArrayList();
        for (String item : this.schoolList)
            if (item.toLowerCase().contains(filterText.toLowerCase()))
                filteredList.add(item);

        return filteredList;
    }

    protected Boolean handleContinious() {
        boolean schooltest = manageSchool();
        return schooltest;
    }

    public boolean isASchoolText(String text) {
        for (String element : data)
            if (element.equals(text))
                return true;
        return false;
    }

    private Boolean manageSchool() {
        if (!isASchoolText(textFieldSchool.getText())) {
            textFieldSchool.getStyleClass().add("errortextfield");
            textFieldSchool.textProperty().addListener((observable, oldvalue, newvalue) -> {
                textFieldSchool.getStyleClass().removeAll(isASchoolText(textFieldSchool.getText())
                        ? "errortextfield"
                        : "");
                textFieldSchool.getStyleClass().add(!isASchoolText(textFieldSchool.getText())
                        ? "errortextfield"
                        : "");
            });
        }
        return isASchoolText(textFieldSchool.getText());
    }

}