package edu.ssng.ing1.sirius.client;

import java.io.IOException;

import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.commons.ClientConnexion;
import edu.ssng.ing1.sirius.client.controllers.commons.UserInfo;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPopUp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainClient extends Application {

    public static VBox notificationVBOX;

    @Override
    public void start(Stage primaryStage) throws Exception {
       


        try {
            LoadingNotificationPanel();
        } catch (IOException e) {
            System.out.println("Error in loading Notification Panel :" + e.getMessage());
        }
        UserInfo.getInstance();
        Router router = Router.getInstance();
        primaryStage.setOnCloseRequest(event -> ClientConnexion.closersocket());
        router.setStage(primaryStage);
        router.setFullScreenStage();
        Student user = UserInfo.getUser();
        if (user == null)
            router.navigateTo("authentification");
        else {
            new HomeBuild();
        }
    }
    private static void LoadingNotificationPanel() throws IOException{
        FXMLLoader homeFXML =RouterPopUp.loadFxml("main/ssn-home");
        Parent parent = homeFXML.load();
        Scene scene = new Scene(parent);
        scene.setRoot(parent);
        notificationVBOX = (VBox) scene.lookup("#notificationVBOX");


        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        System.out.println(notificationVBOX);

    }

    public static void main(String[] args) {
        launch(args);
    }

}