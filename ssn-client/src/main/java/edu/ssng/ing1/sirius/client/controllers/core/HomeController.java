package edu.ssng.ing1.sirius.client.controllers.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import edu.ssng.ing1.sirius.client.MainClient;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPoUp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

public class HomeController implements Initializable {
    @FXML
    private Button friendPageBtn;
    @FXML
    private Button createActivityBtn;
    @FXML
    private BorderPane corePane;
    @FXML
    private SplitPane homePane;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ScrollPane homeScrollPane;
    @FXML
    private ScrollPane postScroolPane;
    @FXML
    private Button homePageBtn;

    @FXML
    private Button seeActivitybtn;

    RouterPoUp routerPoUp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        routerPoUp = RouterPoUp.getInstance();
        createActivityBtn.setOnAction(event -> {
            System.out.println("je marcheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            routerPoUp.navigateTo("createActivityPage1");
        });
        // String ssnemailpref = "SSN_USER_EMAIL";
        // Preferences prefs = Preferences.userRoot().node(MainClient.class.getName());
        // String userEmail = prefs.get(ssnemailpref, null);
        // if (userEmail != null) {
        // System.out.println(userEmail);
        // logger.debug("hummmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
        // }

        // scene.addEventFilter(ScrollEvent.ANY, event -> {
        // if (event.getTarget() == scene || event.getTarget() == postScroolPane) {
        // double deltaY = event.getDeltaY();
        // double currentVvalue = postScroolPane.getVvalue();
        // double newVvalue = currentVvalue - deltaY /
        // postScroolPane.getContent().getBoundsInLocal().getHeight();
        // postScroolPane.setVvalue(Math.max(0, Math.min(1, newVvalue)));
        // event.consume();
        // }
        // });
        friendPageBtn.setOnAction(event -> {

            try {
                Router router = Router.getInstance();
                FXMLLoader signinLoader = router.getParentNode("friend-page");
                BorderPane signinPane = (BorderPane) signinLoader.load();
                corePane.setCenter(signinPane);
            } catch (IOException e) {
                System.out.println(e);
            }
        });
        homePageBtn.setOnAction(event -> {
            try {
                corePane.setCenter(homePane);
            } catch (Exception e) {
                System.out.println(e);
            }
        });

    }

    @FXML
   public void seeActivity(){
    routerPoUp.navigateTo("activityCreated");

    }
    @FXML
    public void closePage(){

        routerPoUp.getStage().close();
        
    }


    public static InputStream deserializeImage(String base64EncodedImage) {
        byte[] bytes = java.util.Base64.getDecoder().decode(base64EncodedImage);
        return new ByteArrayInputStream(bytes);
    }

}
