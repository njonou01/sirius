package edu.ssng.ing1.sirius.client.controllers.authentification;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.requests.authentification.AuthRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class SignUpFinalController implements Initializable {

    @FXML
    private Button signupBtn;
    @FXML
    private CheckBox acceptCheckBox;

    public Button getSignupBtn() {
        return signupBtn;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signupBtn.setDisable(true);
        acceptCheckBox.setOnAction(event -> {
            signupBtn.setDisable(!acceptCheckBox.isSelected());
        });
    }

    public CheckBox getAcceptCheckBox() {
        return acceptCheckBox;
    }

    protected void setStudentInfo(Student student) {
        try {
            AuthRequest.signUpAs(student);
        } catch (NullPointerException | IOException | InterruptedException e) {
        }
    }

}
