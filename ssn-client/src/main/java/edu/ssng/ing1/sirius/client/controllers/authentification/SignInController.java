package edu.ssng.ing1.sirius.client.controllers.authentification;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.HomeBuild;
import edu.ssng.ing1.sirius.client.controllers.commons.UserInfo;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.toast.Toast;
import edu.ssng.ing1.sirius.client.toast.ToastType;
import edu.ssng.ing1.sirius.requests.authentification.AuthRequest;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SignInController implements Initializable {
    @FXML TextField emailField;

    @FXML
    private Label invalidEmail;

    @FXML
    private Label invalidPassword;

    @FXML PasswordField passwordField;

    @FXML
    private CheckBox remindCheckBox;

    @FXML
    private Hyperlink resetPaswordLink;

    @FXML
    private Button showpasswordBtn;

    @FXML
    private Button signinBtn;

    @FXML
    private Pane signingPane;

    @FXML
    private TextField textFielPassword;

    private Boolean passwordIsVisible = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validateField(emailField, Rules::isValidEmail);
        validateField(passwordField, password -> !getValueOf(passwordField).isEmpty());
        textFielPassword.textProperty().bindBidirectional(passwordField.textProperty());
        showpasswordBtn.setOnAction(event -> {
            textFielPassword.setVisible(this.passwordIsVisible);
            passwordField.setVisible(!this.passwordIsVisible);
            this.passwordIsVisible = !this.passwordIsVisible;
        });

        signinBtn.setOnAction(event -> {
            if (Rules.isValidEmail(emailField.getText()) && !(getValueOf(passwordField).isEmpty())) {
                try {

                    handleSignIn();

                } catch (NullPointerException | IOException | InterruptedException e) {
                    Toast.buildToast(ToastType.ERROR,
                            "Une Erreur est survenu, veillez re-essayer plustard");
                }
            } else {

            }
        });
    }

    public void handleSignIn() throws NullPointerException, IOException, InterruptedException {
        Student currUser = new Student(emailField.getText(), passwordField.getText());
        Boolean isValidRegister = AuthRequest.signInAs(currUser);

        if (isValidRegister) {
            UserInfo.registerUser(emailField.getText());
            Platform.runLater(() -> {
                new HomeBuild();
            });

        } else
            Toast.buildToast(ToastType.ERROR,
                    "L'utilisateur n'existe pas our le mot de passe est incorrect");
    }

    private void validateField(TextField field, Function<String, Boolean> validator) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            field.getStyleClass().removeAll(validator.apply(newValue)
                    ? "errortextfield"
                    : "");
            field.getStyleClass().add(!validator.apply(newValue)
                    ? "errortextfield"
                    : "");
            if (field == emailField)
                invalidEmail.setVisible(!Rules.isValidEmail(emailField.getText()));
            if (field == passwordField)
                invalidPassword.setVisible(getValueOf(passwordField).isEmpty());

        });
    }

    public String getValueOf(TextField textField) {
        return (textField.getText() == null || textField.getText().isBlank()) ? "" : textField.getText();
    }

    public AnchorPane getAuthpane() {
        VBox root = (VBox) (Router.getInstance().getStage().getScene()).getRoot();
        return (AnchorPane) root.getChildren().get(0);
    }
}