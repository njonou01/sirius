package edu.ssng.ing1.sirius.client.controllers.authentification;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;

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
    private TextField textFieldSchool;
    @FXML
    private CheckBox studyCurentlyCheck;

    public Button getNextBtn() {
        return nextBtn;
    }

    private String data[] = { "Apple", "Banana", "Orange", "Peach", "Grapes", "Pineapple" };

    private ObservableList<String> schoolList = FXCollections.observableArrayList(
            data);
    FilteredList<String> filteredItems = new FilteredList<>(schoolList);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studyCurentlyCheck.setOnAction(event -> {
            endCombobox.setDisable(studyCurentlyCheck.isSelected());
        });
        initStartCombobox(30, 0);
        schoolCombobox.setItems(filteredItems);
        schoolCombobox.setCellFactory(param -> new ComboBoxListCell());
        textFieldSchool.textProperty().addListener((observable, oldValue, newValue) -> {
            final String filter = newValue.toLowerCase();
            filteredItems.setPredicate(item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; 
                }
                return item.toLowerCase().contains(filter); 
            });
            schoolCombobox.show();

        });


        

    }

    private void initStartCombobox(int begin, int end) {
        int currentYear = LocalDate.now().getYear();
        for (int year = currentYear - begin; year < currentYear + 1 + end; year++) {
            startCombobox.getItems().add(year);
        }
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
        
        return isASchoolText("");
    }

}