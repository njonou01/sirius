package edu.ssng.ing1.sirius.client;

import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.controllers.commons.UserInfo;
import edu.ssng.ing1.sirius.client.router.Router;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        UserInfo.getInstance();
        Router router = Router.getInstance();
        router.setStage(primaryStage);
        router.setFullScreenStage();
        Student user = UserInfo.getUser();
        if (user == null)
            router.navigateTo("authentification");
        else {
            new HomeBuild();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}