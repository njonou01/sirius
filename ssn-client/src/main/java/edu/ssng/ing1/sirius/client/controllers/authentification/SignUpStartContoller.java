package edu.ssng.ing1.sirius.client.controllers.authentification;


import edu.ssng.ing1.sirius.client.router.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignUpStartContoller {

    @FXML
    private Button agreeBtn;

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
    private Hyperlink signLink;


    @FXML
    void initialize() {
    }

    @FXML
    public void agreeAndGoToNextPage(ActionEvent event) {
        Router router = Router.getInstance();
        router.navigateTo("signup_student");
    }


}
