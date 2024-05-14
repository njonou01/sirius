package edu.ssng.ing1.sirius.client.controllers.createActivity;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.controllers.commons.UserInfo;
import edu.ssng.ing1.sirius.client.router.RouterPopUp;
import edu.ssng.ing1.sirius.client.toast.Toast;
import edu.ssng.ing1.sirius.client.toast.ToastType;

// import edu.ssng.ing1.sirius.MainInsertClient;

import edu.ssng.ing1.sirius.requests.activities.InsertActivityQuery;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * createActivityPage2Controller
 */
public class createActivityPage4Controller implements Initializable {
    RouterPopUp router;

    Button overBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        router = RouterPopUp.getInstance();

    }

    @FXML
    public void insertActivity() {
        Student student = UserInfo.getUser();
        long timestamp = System.currentTimeMillis(); // Par exemple
        Timestamp ts = new Timestamp(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestampString = sdf.format(ts);
        RouterPopUp.activite.setId_student(student.getId_student());
        RouterPopUp.activite.setNomCreateur(student.getFirstname());
        RouterPopUp.activite.setProvenance("HomePage");
        RouterPopUp.activite.setConfidentialite("Privé");
        RouterPopUp.activite.setDatecreation(timestampString);
        RouterPopUp.activite.setState(true);
        Toast.buildToast(ToastType.SUCCESS, "Félicitation Activité Créée ");
        try {
            InsertActivityQuery.InsertActivite(RouterPopUp.activite);
        } catch (IOException | InterruptedException | SQLException e) {
            System.out.println("OOOOOOOOOOOOO" + e.getMessage() + "OOOOOOOOOOOOO");
        }
        

        router.getStage().close();

    }

    @FXML
    public void closePage() {
       

        router.getStage().close();

    }

}