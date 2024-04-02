package edu.ssng.ing1.sirius.client.controllers.authentification;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.MainClient;
import edu.ssng.ing1.sirius.client.requests.authentification.AuthRequest;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.toast.Toast;
import edu.ssng.ing1.sirius.client.toast.ToastType;
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
                try {
                    if (AuthRequest.signInAs(new Student(emailField.getText(), passwordField.getText()))) {
                        String ssnemailpref = "SSN_USER_EMAIL";

                        Preferences prefs = Preferences.userRoot().node(MainClient.class.getName());
                        prefs.put(ssnemailpref, emailField.getText());

                        Router.getInstance().navigateTo("main");
                    } else
                        Toast.buildToast(ToastType.ERROR,
                                "L'utilisateur n'existe pas our le mot de passe est incorrect");
                } catch (NullPointerException | IOException | InterruptedException e) {
                    Toast.buildToast(ToastType.ERROR, "Une Erreur est survenu, veillez re-essayer plustard");
                }

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
