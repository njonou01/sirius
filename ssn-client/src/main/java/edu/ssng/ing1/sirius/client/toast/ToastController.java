package edu.ssng.ing1.sirius.client.toast;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class ToastController {

    @FXML
    private Text toastMessage;

    @FXML
    private FontIcon toastIcon;

    @FXML
    private Label toastTitle;

    protected void setToastMessage(String msg){
        toastMessage.setText(msg);
    }
    protected void setIcon(String literal){
        toastIcon.setIconLiteral(literal);
    }
    protected void setTitle(String title){
        toastTitle.setText(title);
    }

}
