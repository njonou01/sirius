package edu.ssng.ing1.sirius.client.controllers.authentification;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

public class SignUpStudentController {

    @FXML
    private Button continiuousBtn;

    @FXML
    private ComboBox<Integer> endYearList;

    @FXML
    private CheckBox iHaveSixteenYear;

    @FXML
    private ComboBox<String> schoolList;

    @FXML
    private ComboBox<Integer> startYearList;

    @FXML
    public void checkTheBoxHaveSixteenYear(Event event) {

    }

    @FXML
    public void initialize() {
        schoolList.setEditable(true);
        startYearList.setEditable(true);
        iHaveSixteenYear.setSelected(true);
        endYearList.setEditable(true);
    }

}
