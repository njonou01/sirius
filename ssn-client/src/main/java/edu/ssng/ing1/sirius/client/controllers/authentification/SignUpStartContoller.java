package edu.ssng.ing1.sirius.client.controllers.authentification;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

public class SignUpStartContoller {

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordConfirmField;

    @FXML
    private Button passwordBtn;

    @FXML
    private Button passwordConfirmBtn;

    @FXML
    private Button nextPageBtn;

    @FXML
    void initialize() {
    }

    @FXML
    public void agreeAndGoToNextPage(ActionEvent event) {
        Button btn = (Button) event.getTarget();
        System.out.println(btn.getParent().getParent());
    }

    public Button getPasswordBtn() {
        return passwordBtn;
    }

    public Button getPasswordConfirmBtn() {
        return passwordConfirmBtn;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public PasswordField getPasswordConfirmField() {
        return passwordConfirmField;
    }
    
    protected void verifyPassword(ActionEvent event){

    }
    protected void verifyPassAndConfirmPass(ActionEvent event){
        
    }
    public Button getNextPageBtn() {
        return nextPageBtn;
    }
    

}
