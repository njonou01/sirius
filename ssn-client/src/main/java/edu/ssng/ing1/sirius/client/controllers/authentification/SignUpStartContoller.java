package edu.ssng.ing1.sirius.client.controllers.authentification;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.kordamp.ikonli.javafx.FontIcon;

import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.requests.authentification.AuthRequest;
import edu.ssng.ing1.sirius.client.toast.Toast;
import edu.ssng.ing1.sirius.client.toast.ToastType;
import javafx.css.Rule;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class SignUpStartContoller {
    @FXML
    private Label emailErrorMsg;
    @FXML
    private Button passwordConfirmBtn;
    @FXML
    private Label passwordConfirmErrorMsg;
    @FXML
    private TextField emailField;
    @FXML
    private Button nextPageBtn;
    @FXML
    private Button passwordBtn;
    @FXML
    private PasswordField passwordConfirmField;
    @FXML
    private Button passwordErrorMsg;
    @FXML
    private PasswordField passwordField;
    @FXML
    private FontIcon showConfirmPasswordIcon;
    @FXML
    private FontIcon showPasswordIcon;
    @FXML
    private TextField textFielPassword;
    @FXML
    private TextField textFielPasswordConfirm;
    @FXML
    private VBox passwordErrorPrecision;

    @FXML
    private Button showPasswordPrecision;
    @FXML
    private FontIcon atLeastOneCapitalLetterIcon;

    @FXML
    private FontIcon atLeastOneLowerCaseIcon;

    @FXML
    private FontIcon atLeastOneSpecialCaractercon;
    @FXML
    private FontIcon isMinEightCaractersIcon;
    @FXML
    private FontIcon noSpaceIcon;

    private Boolean passwordIsVisible = false;
    private Boolean passwordConfirmIsVisible = false;

    private HBox emailErrorParent;
    private HBox passwordErrorParent;
    private HBox passwordConfirmErrorParent;

    private void setIconAfterVerif(FontIcon icon, boolean status) {
        if (status) {
            icon.setIconLiteral("fas-check");
            icon.setIconColor(Color.web("#32dd07"));
        } else {
            icon.setIconLiteral("fas-times");
            icon.setIconColor(Color.web("#ff0000"));
        }
    }

    @FXML
    void initialize() {
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            setIconAfterVerif(isMinEightCaractersIcon, Rules.verifyLength(newValue));
            setIconAfterVerif(atLeastOneCapitalLetterIcon, Rules.verifyUppercase(newValue));
            setIconAfterVerif(atLeastOneLowerCaseIcon, Rules.verifyLowercase(newValue));
            setIconAfterVerif(atLeastOneSpecialCaractercon, Rules.verifySpecial(newValue));
            setIconAfterVerif(noSpaceIcon, Rules.checkNoSpace(newValue));
        });
        showPasswordPrecision.setOnMouseEntered(e -> {
            passwordErrorPrecision.setVisible(true);
        });

        showPasswordPrecision.setOnMouseExited(e -> {
            passwordErrorPrecision.setVisible(false);
        });
        this.emailErrorParent = (HBox) emailErrorMsg.getParent();
        this.passwordErrorParent = (HBox) passwordErrorMsg.getParent();
        this.passwordConfirmErrorParent = (HBox) passwordConfirmErrorMsg.getParent();
        removeErrorMsgFrom("all");

        textFielPassword.setVisible(this.passwordIsVisible);
        textFielPasswordConfirm.setVisible(this.passwordConfirmIsVisible);

        textFielPassword.textProperty().bindBidirectional(passwordField.textProperty());
        passwordBtn.setOnAction(event -> {
            showPasswordIcon.setIconLiteral(showPasswordIcon.getIconLiteral() == "fas-eye"
                    ? "far-eye-slash"
                    : "fas-eye");
            textFielPassword.setVisible(!this.passwordIsVisible);
            passwordField.setVisible(this.passwordIsVisible);
            this.passwordIsVisible = !this.passwordIsVisible;
        });
        textFielPasswordConfirm.textProperty().bindBidirectional(passwordConfirmField.textProperty());
        passwordConfirmBtn.setOnAction(event -> {
            showConfirmPasswordIcon.setIconLiteral(showConfirmPasswordIcon.getIconLiteral() == "fas-eye"
                    ? "far-eye-slash"
                    : "fas-eye");
            textFielPasswordConfirm.setVisible(!this.passwordConfirmIsVisible);
            passwordConfirmField.setVisible(this.passwordConfirmIsVisible);
            this.passwordConfirmIsVisible = !this.passwordConfirmIsVisible;
        });
    }

    private void addErrorMsgFrom(String field) {
        switch (field) {
            case "email":
                emailErrorParent.getChildren().setAll(emailErrorMsg);
                break;
            case "password":
                passwordErrorParent.getChildren().setAll(passwordErrorMsg);
                break;
            case "confirm-password":
                passwordConfirmErrorParent.getChildren().setAll(passwordConfirmErrorMsg);
                break;
            default:
                break;
        }
    }

    private void removeErrorMsgFrom(String field) {
        switch (field) {
            case "all":
                emailErrorParent.getChildren().remove(emailErrorMsg);
                passwordErrorParent.getChildren().remove(passwordErrorMsg);
                passwordConfirmErrorParent.getChildren().remove(passwordConfirmErrorMsg);
                break;
            case "email":
                if (!emailErrorParent.getChildren().isEmpty())
                    emailErrorParent.getChildren().remove(emailErrorMsg);
                break;
            case "password":
                if (!passwordErrorParent.getChildren().isEmpty())
                    passwordErrorParent.getChildren().remove(passwordErrorMsg);
                break;
            case "confirm-password":
                if (!passwordConfirmErrorParent.getChildren().isEmpty())
                    passwordConfirmErrorParent.getChildren().remove(passwordConfirmErrorMsg);
                break;
            default:
                break;
        }
    }

    protected Boolean handleContinious() {
        Boolean isValidEmail = isValidEmail();
        Boolean isValidConfirmPass = isValidConfirmPassWord();
        Boolean isValidPass = isValidPassword();
        Boolean isStudentExist = true;
        try {
            isStudentExist = doesStudentExist();
        } catch (NullPointerException | IOException | InterruptedException e) {
            isStudentExist = true;
        }
        return (isValidEmail && isValidPass && isValidConfirmPass && !isStudentExist);
    }

    private Boolean isValidEmail() {
        if (!Rules.isValidEmail(emailField.getText())) {
            validateField(emailField, Rules::isValidEmail);
        }

        return Rules.isValidEmail(emailField.getText());
    }

    private Boolean isValidConfirmPassWord() {
        if (!areSimilarePassword(passwordConfirmField.getText())) {
            validateField(passwordConfirmField, password -> areSimilarePassword(password));
            validateField(textFielPasswordConfirm, password -> areSimilarePassword(password));
        }
        return (passwordConfirmField.getText()).equals(passwordField.getText());
    }

    private Boolean doesStudentExist() throws NullPointerException, IOException, InterruptedException {
        Boolean isStudentExist;
        isStudentExist = AuthRequest.isUser(new Student(emailField.getText()));
        return isStudentExist ? true : false;
    }

    private Boolean isValidPassword() {
        if (!Rules.isValidPassword(passwordField.getText())) {
            validateField(passwordField, Rules::isValidPassword);
            validateField(textFielPassword, Rules::isValidPassword);
        }
        return Rules.isValidPassword(passwordField.getText());
    }

    private Boolean areSimilarePassword(String value) {
        return value.equals(passwordField.getText());
    }

    protected Button getNextPageBtn() {
        return nextPageBtn;
    }

    private void validateField(TextField field, Function<String, Boolean> validator) {
        if (!validator.apply(field.getText())) {
            addErrorMsgFrom(getFielName(field));
        } else
            removeErrorMsgFrom(getFielName(field));
        field.getStyleClass().add("errortextfield");
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            field.getStyleClass().removeAll(validator.apply(newValue)
                    ? "errortextfield"
                    : "");
            field.getStyleClass().add(!validator.apply(newValue)
                    ? "errortextfield"
                    : "");
            if (field.getStyleClass().contains("errortextfield")) {
                addErrorMsgFrom(getFielName(field));
            } else
                removeErrorMsgFrom(getFielName(field));
        });
    }

    
    private String getFielName(TextField field) {
        if (field.equals(emailField))
            return "email";
        if (field.equals(passwordField))
            return "password";
        if (field.equals(passwordConfirmField))
            return "confirm-password";
        return "bad field";
    }
    private void removeAllClassError(Node ... nodes){
        for (Node node : nodes) {
         node.getStyleClass().removeAll("errortextfield")   ;
        }
    }

    protected void renitialize() {
        removeAllClassError(emailField,passwordField,passwordConfirmField,textFielPassword,textFielPasswordConfirm);
        removeErrorMsgFrom("all");
        emailField.clear();
        passwordConfirmField.clear();
        passwordField.clear();
        textFielPassword.clear();
        textFielPasswordConfirm.clear();
    }

    protected void setStudentData(Student student) {
        student.setEmail(emailField.getText());
        student.setPassword(passwordField.getText());
    }

}
