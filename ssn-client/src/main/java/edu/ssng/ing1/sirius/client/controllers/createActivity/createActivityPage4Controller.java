package edu.ssng.ing1.sirius.client.controllers.createActivity;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

// import edu.ssng.ing1.sirius.MainInsertClient;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPoUp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * createActivityPage2Controller
 */
public class createActivityPage4Controller implements Initializable {
    RouterPoUp router;

    Button overBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        router = RouterPoUp.getInstance();

    }

    @FXML
    public void insertActivity() {
        RouterPoUp.activite.setId_student(1);
        RouterPoUp.activite.setNomCreateur("Eloka");
        RouterPoUp.activite.setProvenance("HomePage");
        RouterPoUp.activite.setConfidentialite("Privé");
        RouterPoUp.activite.setDatecreation("1990-05-03 20:00:00");
        // try {
        //     MainInsertClient.InsertActivite(Router.activite);
        // } catch (IOException | InterruptedException | SQLException e) {
        //     System.out.println("OOOOOOOOOOOOO"+e.getMessage()+"OOOOOOOOOOOOO");
        // }

    }

    @FXML
    public void closePage() {

        router.getStage().close();

    }

}