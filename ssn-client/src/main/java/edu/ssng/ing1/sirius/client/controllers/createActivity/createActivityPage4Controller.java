package edu.ssng.ing1.sirius.client.controllers.createActivity;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

// import edu.ssng.ing1.sirius.MainInsertClient;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPoUp;
import edu.ssng.ing1.sirius.client.toast.Toast;
import edu.ssng.ing1.sirius.client.toast.ToastType;
import edu.ssng.ing1.sirius.requests.activities.InsertActivityQuery;
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
        RouterPoUp.activite.setState(true);
        try {
            InsertActivityQuery.InsertActivite(RouterPoUp.activite);
        } catch (IOException | InterruptedException | SQLException e) {
            System.out.println("OOOOOOOOOOOOO"+e.getMessage()+"OOOOOOOOOOOOO");
        }
        Toast.buildToast(ToastType.SUCCESS, "Félicitation Activité Créée ");

        router.getStage().close();

    }

    @FXML
    public void closePage() {

        router.getStage().close();

    }

}