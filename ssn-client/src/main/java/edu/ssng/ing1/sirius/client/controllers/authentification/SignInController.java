package edu.ssng.ing1.sirius.client.controllers.authentification;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignInController {

    @FXML
    private Label emailErrorMsg;

    @FXML
    private TextField emailField;

    @FXML
    private Label passwordErrorMsg;

    @FXML
    private TextField passwordField;

    @FXML
    private Button showBtn;

    @FXML
    private Button signinBtn;

    @FXML
    private Hyperlink signupLink;

}