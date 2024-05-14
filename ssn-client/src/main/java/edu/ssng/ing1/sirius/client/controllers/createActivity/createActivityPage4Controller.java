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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * createActivityPage2Controller
 */
public class createActivityPage4Controller implements Initializable {
    RouterPopUp router;

    Button overBtn;

    

    @FXML
    Button inviteFriendsBtn;

    @FXML
    Button lastActivityBtn;

    @FXML
    VBox friendVBox;
    
    @FXML
    VBox inviteVbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        router = RouterPopUp.getInstance();

        Platform.runLater(() -> {
            Student student = UserInfo.getUser();
            lastActivityBtn.setOnAction(event -> displayFriendsInvite(student));
        });

    }

    @FXML
    public void insertActivity() {
        Student student = UserInfo.getUser();
        long timestamp = System.currentTimeMillis(); 
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

    public void displayFriendsInvite(Student student){
        Platform.runLater(() -> {
            System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
            System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
            System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");



            Label labelInvite= new Label("Amis Invité");
            labelInvite.getStyleClass().add("InvitSend");


            HBox leftBoxStudent = new HBox();
            leftBoxStudent.getStyleClass().add("leftBoxStudent");
            leftBoxStudent.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            leftBoxStudent.setPrefHeight(77.0);
            leftBoxStudent.setPrefWidth(254.0);

            ImageView imageView = new ImageView();
            imageView.setFitHeight(65.0);
            imageView.setFitWidth(60.0);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);

            VBox friendVBox = new VBox();
            friendVBox.setAlignment(javafx.geometry.Pos.CENTER);
            friendVBox.setPrefHeight(77.0);
            friendVBox.setPrefWidth(200.0);
            friendVBox.setSpacing(20.0);
            friendVBox.setId("friendVBox");

            Label label = new Label("Nom :" +student.getFamilyname()+ " prenom :"+student.getFirstname());

            Button inviteFriendsBtn = new Button("Inviter");
            inviteFriendsBtn.setId("inviteFriendsBtn");
            inviteFriendsBtn.getStyleClass().add("btn");
            inviteFriendsBtn.setMnemonicParsing(false);

            

            friendVBox.getChildren().addAll(label, inviteFriendsBtn);
            int index= friendVBox.getChildren().indexOf(inviteFriendsBtn);

            
            leftBoxStudent.getChildren().addAll(imageView, friendVBox);
            inviteFriendsBtn.setOnAction(event->{
                friendVBox.getChildren().set(index, labelInvite);
            });

            inviteVbox.getChildren().add(friendVBox);


        });
            
            
            
       
    }

}