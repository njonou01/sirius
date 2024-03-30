package edu.ssng.ing1.sirius.client.controllers.authentification;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class SignInController implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private CheckBox remindCheckBox;

    @FXML
    private Hyperlink resetPaswordLink;

    @FXML
    private Button showpasswordBtn;

    @FXML
    private Button signinBtn;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Pane signingPane;

    @FXML
    private TextField textFielPassword;

    private Boolean passwordIsVisible = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFielPassword.textProperty().bindBidirectional(passwordField.textProperty());
        showpasswordBtn.setOnAction(event -> {
            textFielPassword.setVisible(this.passwordIsVisible);
            passwordField.setVisible(!this.passwordIsVisible);
            this.passwordIsVisible = !this.passwordIsVisible;
        });

        signinBtn.setOnAction(event -> {
            if (Rules.isValidEmail(emailField.getText())) {

            } else {
                emailField.getStyleClass().add("errortextfield");
                emailField.textProperty().addListener((observable, oldvalue, newvalue) -> {
                    emailField.getStyleClass().removeAll(Rules.isValidEmail(newvalue) ? "errortextfield" : "");
                    emailField.getStyleClass().add(!Rules.isValidEmail(newvalue) ? "errortextfield" : "");
                });
            }
        });
    }
}
