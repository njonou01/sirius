package edu.ssng.ing1.sirius.client.controllers.authentification;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ToastController {

    @FXML
    private Text toastMessage;

    protected void setToastMessage(String msg){
        toastMessage.setText(msg);
    }

}
