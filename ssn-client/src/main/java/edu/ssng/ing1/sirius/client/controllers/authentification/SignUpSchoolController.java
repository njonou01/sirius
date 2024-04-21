package edu.ssng.ing1.sirius.client.controllers.authentification;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import edu.ssng.ing1.sirius.business.dto.Universities;
import edu.ssng.ing1.sirius.business.dto.University;
import edu.ssng.ing1.sirius.requests.authentification.CommonRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
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
    private TextField textFieldSchool;

    @FXML
    private TextArea interestField;

    private Set<University> universities;

    public Button getNextBtn() {
        return nextBtn;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            universities = CommonRequest.selectUniversities().getUniversities();

        } catch (Exception e) {
            universities = new Universities().getUniversities();
        }
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

        List<String> filterItems = universities.stream()
                .filter(item -> {
                    String filtertext = filterText.toLowerCase();
                    String label = item.getLabel();
                    String acronym = item.getAcronym();
                    String shortname = item.getShortname();
                    return ((label.toLowerCase().contains(filtertext))
                            ||
                            (shortname == null ? false : shortname.toLowerCase().contains(filtertext))
                            ||
                            (acronym == null ? false : acronym.toLowerCase().contains(filtertext)));
                }).map(item -> item.getLabel().substring(0, 1).toUpperCase() + item.getLabel().substring(1))
                .collect(Collectors.toList());
        filteredList.setAll(filterItems);
        return filteredList;
    }

    protected Boolean handleContinious() {
        boolean schooltest = manageSchool();
        boolean starttest = managestartSchool();
        System.out.println(starttest);
        return schooltest;
    }

    public boolean isASchoolText(String text) {
        List<University> filterUniversity = universities.stream()
                .filter(item -> item.getLabel().toLowerCase().equals(text.toLowerCase()))
                .collect(Collectors.toList());
        return filterUniversity.isEmpty() ? false : true;
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

    private Boolean managestartSchool() {
        startCombobox.valueProperty().addListener((observable, oldValue, newValue) -> {
            startCombobox.getStyleClass().removeAll(newValue != null
                    ? "errortextfield"
                    : "");
            startCombobox.getStyleClass().add(newValue == null
                    ? "errortextfield"
                    : "");
        });
        startCombobox.getStyleClass().add(startCombobox.getValue() == null
                ? "errortextfield"
                : "");

        return startCombobox.getValue() != null;
    }

}