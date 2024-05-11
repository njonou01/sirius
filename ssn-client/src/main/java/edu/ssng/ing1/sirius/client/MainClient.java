package edu.ssng.ing1.sirius.client;

import java.io.IOException;

import edu.ssng.ing1.sirius.business.dto.Message;
import edu.ssng.ing1.sirius.business.dto.Messages;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.controllers.commons.UserInfo;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.requests.friend.FriendCommonRequest;
import edu.ssng.ing1.sirius.requests.messages.CommonsMessageRequest;
import javafx.application.Application;
import javafx.stage.Stage;
import okhttp3.Route;

public class MainClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Router router = Router.getInstance();
        // router.setStage(primaryStage);
        // router.setFullScreenStage();
        // router.navigateTo("messaging");
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
        // UserInfo.getInstance();
        // Student user = UserInfo.getUser();
        // try {
        //     Messages messages = CommonsMessageRequest.selectMessages(user.getId_student());
        //     for (Message message : messages.getMessages()) {
        //         System.out.println(message.toString());
        //     }
        //     // FriendCommonRequest.selectSuggestedFriends(new Student(user.getId_student()));
        // } catch (IOException | InterruptedException e) {
        //     System.out.println("erreur");
        // }
    }

}