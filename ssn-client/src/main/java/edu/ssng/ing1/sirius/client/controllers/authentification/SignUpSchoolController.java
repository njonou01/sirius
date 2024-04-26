package edu.ssng.ing1.sirius.client.controllers.authentification;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.dlsc.gemsfx.SearchField;
import com.dlsc.gemsfx.YearMonthPicker;

import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.business.dto.Universities;
import edu.ssng.ing1.sirius.business.dto.University;
import edu.ssng.ing1.sirius.requests.authentification.CommonRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class SignUpSchoolController implements Initializable {

    @FXML
    private TextField trainingFollowed;

    @FXML
    private TextArea formationDescription;

    @FXML
    private YearMonthPicker formationEnd;

    @FXML
    private YearMonthPicker formationStart;

    @FXML
    private Button nextBtn;

    @FXML
    private SearchField<University> textFieldSchool;

    private Set<University> universities;

    public Button getNextBtn() {
        return nextBtn;
    }

    private void initSearchUniversity() {
        textFieldSchool.setSuggestionProvider(request -> universities.stream()
                .filter(item -> {
                    String filtertext = request.getUserText().toLowerCase();
                    String label = item.getLabel();
                    String acronym = item.getAcronym();
                    String shortname = item.getShortname();
                    return ((label.toLowerCase().contains(filtertext))
                            ||
                            (shortname == null ? false : shortname.toLowerCase().contains(filtertext))
                            ||
                            (acronym == null ? false : acronym.toLowerCase().contains(filtertext)));

                })
                .collect(Collectors.toList()));

        textFieldSchool.setConverter(new StringConverter<University>() {
            @Override
            public String toString(University country) {
                if (country != null) {
                    return country.getLabel();
                }
                return "";
            }

            @Override
            public University fromString(String string) {
                return new University();
            }

        });

        textFieldSchool.setMatcher(
                (broker, searchText) -> broker.getLabel().toLowerCase().startsWith(searchText.toLowerCase()));

        textFieldSchool.setComparator(Comparator.comparing(University::getLabel));

        textFieldSchool.getEditor().setPromptText("Entrer une Ecole ...");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            universities = CommonRequest.selectUniversities().getUniversities();

        } catch (Exception e) {
            universities = new Universities().getUniversities();
        }
        initSearchUniversity();
    }

    protected Boolean handleContinious() {
        boolean schooltest = manageSchool();
        boolean endtest = manageStop();
        boolean starttest = manageStart();
        boolean diplomatest = manageTrainingFollowed();

        return schooltest && endtest&& starttest&& diplomatest;
    }

    private boolean isASchoolText(String text) {
        List<University> filterUniversity = universities.stream()
                .filter(item -> item.getLabel().toLowerCase().equals(text.toLowerCase()))
                .collect(Collectors.toList());
        return filterUniversity.isEmpty() ? false : true;
    }

    private Boolean manageSchool() {
        if (!isASchoolText(textFieldSchool.getText())) {
            validateField(textFieldSchool, (school) -> isASchoolText(school));
        }
        return isASchoolText(textFieldSchool.getText());
    }

    private Boolean manageStart() {
        System.out.println(formationStart.getValue().toString() + "-01");
        if (!Rules.isValidDate(formationStart.getValue().toString() + "-01")) {
            validateField(formationStart, (start) -> Rules.isValidDate(start + "-01"));
        }
        return Rules.isValidDate(formationStart.getValue().toString() + "-01");
    }

    private Boolean manageStop() {
        if (!Rules.isValidDate(formationEnd.getValue().toString() + "-01")) {
            validateField(formationEnd, (end) -> Rules.isValidDate(end + "-01"));
        }
        return Rules.isValidDate(formationEnd.getValue().toString() + "-01");
    }

    private Boolean manageTrainingFollowed() {
        if ((trainingFollowed.getText() == null || trainingFollowed.getText().isBlank())) {
            validateField(trainingFollowed, (diploma) -> !(diploma == null || diploma.isBlank()));
        }
        return !(trainingFollowed.getText() == null || trainingFollowed.getText().isBlank());
    }

    protected void setStudentData(Student student) {
        student.setUniversity(textFieldSchool.getText());
        student.setFormation_description(formationDescription.getText());
        Date start = Date.valueOf(
                LocalDate.parse(formationStart.getValue().toString() + "-01"));
        Date end = Date.valueOf(
                LocalDate.parse(formationEnd.getValue().toString() + "-01"));
        student.setFormation_start(start);
        student.setFormation_stop(end);
        student.setTraining_followed(trainingFollowed.getText());
    }

    private void validateField(SearchField<University> field, Function<String, Boolean> validator) {
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

    private void validateField(YearMonthPicker field, Function<String, Boolean> validator) {
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