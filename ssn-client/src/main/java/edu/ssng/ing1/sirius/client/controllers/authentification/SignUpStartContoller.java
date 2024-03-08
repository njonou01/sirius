package edu.ssng.ing1.sirius.client.controllers.authentification;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpStartContoller {
    @FXML
    private TextField emailField;
    @FXML
    private Button nextPageBtn;

    @FXML
    private Button passwordBtn;

    @FXML
    private Button passwordConfirmBtn;

    @FXML
    private PasswordField passwordConfirmField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField textFielPassword;

    @FXML
    private TextField textFielPasswordConfirm;

    private Boolean passwordIsVisible = false;
    private Boolean passwordConfirmIsVisible = false;

    @FXML
    void initialize() {
        textFielPassword.setVisible(this.passwordIsVisible);
        textFielPasswordConfirm.setVisible(this.passwordConfirmIsVisible);

        textFielPassword.textProperty().bindBidirectional(passwordField.textProperty());
        passwordBtn.setOnAction(event -> {
            textFielPassword.setVisible(!this.passwordIsVisible);
            passwordField.setVisible(this.passwordIsVisible);
            this.passwordIsVisible = !this.passwordIsVisible;
        });
        textFielPasswordConfirm.textProperty().bindBidirectional(passwordConfirmField.textProperty());
        passwordConfirmBtn.setOnAction(event -> {
            textFielPasswordConfirm.setVisible(!this.passwordConfirmIsVisible);
            passwordConfirmField.setVisible(this.passwordConfirmIsVisible);
            this.passwordConfirmIsVisible = !this.passwordConfirmIsVisible;
        });
    }

    protected Boolean handleContinious() {
        Boolean bool1 = manageEmail();
        Boolean bool2 = manageConfirmPassWord();
        Boolean bool3 = managePassword();
        return (bool1 && bool2 && bool3);
    }

    private Boolean manageEmail() {
        if (!Rules.isValidEmail(emailField.getText())) {
            emailField.getStyleClass().add("errortextfield");
            emailField.textProperty().addListener((observable, oldvalue, newvalue) -> {
                emailField.getStyleClass().removeAll(Rules.isValidEmail(newvalue)
                        ? "errortextfield"
                        : "");
                emailField.getStyleClass().add(!Rules.isValidEmail(newvalue)
                        ? "errortextfield"
                        : "");
            });
        }
        return Rules.isValidEmail(emailField.getText());
    }

    private Boolean manageConfirmPassWord() {
        if (!(passwordConfirmField.getText()).equals(passwordField.getText())) {
            passwordConfirmField.getStyleClass().add("errortextfield");
            passwordConfirmField.textProperty().addListener((observable, oldvalue, newvalue) -> {
                passwordConfirmField.getStyleClass().removeAll(areSimilare(passwordField, newvalue)
                        ? "errortextfield"
                        : "");
                passwordConfirmField.getStyleClass().add(!areSimilare(passwordField, newvalue)
                        ? "errortextfield"
                        : "");
                textFielPasswordConfirm.getStyleClass().removeAll(areSimilare(passwordField, newvalue)
                        ? "errortextfield"
                        : "");
                textFielPasswordConfirm.getStyleClass().add(!areSimilare(passwordField, newvalue)
                        ? "errortextfield"
                        : "");
            });
        }
        System.out.println((passwordConfirmField.getText()).equals(passwordField.getText()));
        return (passwordConfirmField.getText()).equals(passwordField.getText());
    }

    private Boolean managePassword() {
        if (!Rules.isValidPassword(passwordField.getText())) {
            System.out.println(Rules.isValidPassword(passwordField.getText()));
            passwordField.getStyleClass().add("errortextfield");
            passwordField.textProperty().addListener((observable, oldvalue, newvalue) -> {
                passwordField.getStyleClass().removeAll(Rules.isValidPassword(newvalue)
                        ? "errortextfield"
                        : "");
                passwordField.getStyleClass().add(!Rules.isValidPassword(newvalue)
                        ? "errortextfield"
                        : "");
                textFielPassword.getStyleClass().removeAll(Rules.isValidPassword(newvalue)
                        ? "errortextfield"
                        : "");
                textFielPassword.getStyleClass().add(!Rules.isValidPassword(newvalue)
                        ? "errortextfield"
                        : "");
            });
        }
        System.out.println(Rules.isValidPassword(passwordField.getText()));

        return Rules.isValidPassword(passwordField.getText());
    }

    private Boolean areSimilare(PasswordField p1, String p2) {
        return p2.equals(p1.getText());
    }

    public Button getNextPageBtn() {
        return nextPageBtn;
    }

}
