package edu.ssng.ing1.sirius.client.controllers.createActivity;

import java.net.URL;
import java.util.ResourceBundle;

import edu.ssng.ing1.sirius.client.router.RouterPopUp;
import javafx.fxml.Initializable;

public class finishPageActivityController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RouterPopUp routerPopUp = RouterPopUp.getInstance();
        System.out.println("Je maffiche");
        try {
            Thread.sleep(3000); // 3000 millisecondes = 3 secondes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        routerPopUp.getStage().close();
    }

}
