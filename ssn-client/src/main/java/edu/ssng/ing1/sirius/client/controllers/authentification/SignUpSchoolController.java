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

    public Button getNextBtn() {
        return nextBtn;
    }

    private ObservableList<String> schoolList;
    private String[] elements = { "Option 1", "Option 2", "Option 3", "Yess" };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studyCurentlyCheck.setOnAction(event -> {
            endCombobox.setDisable(studyCurentlyCheck.isSelected());
        });
        initStartCombobox(30, 0);
        initSchoolCombobox();
        initSchoolComboboxEvent();
    }

    private void initSchoolCombobox() {
        this.schoolList = FXCollections.observableArrayList(elements);
        schoolCombobox.setItems(FXCollections.observableArrayList(elements));
        schoolCombobox.setEditable(true);

    }

    private void initSchoolComboboxEvent() {
        schoolCombobox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
           if (schoolCombobox.getEditor().getText().trim().isEmpty()) {
            return ;
           }

            schoolCombobox.hide();
            schoolCombobox.setItems(filterItems(newValue));;
            schoolCombobox.show();
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
        for (String item : this.schoolList) {
            if (item.toLowerCase().contains(filterText.toLowerCase())) {
                filteredList.add(item);
            }
        }
        System.out.println(filteredList);

        return filteredList;
    }
}
