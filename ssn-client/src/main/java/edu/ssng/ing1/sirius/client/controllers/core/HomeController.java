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
import edu.ssng.ing1.sirius.client.router.RouterPopUp;
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

    Router router = Router.getInstance();
    RouterPopUp routerPoUp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        routerPoUp = RouterPopUp.getInstance();
        createActivityBtn.setOnAction(event -> {
            routerPoUp.navigateTo("createActivityPage1");
        });

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
    public void seeActivity() {
        router.navigateTo("activityCreated");

    }

    @FXML
    public void seeMyActivity() {
        router.navigateTo("seeMyActivity");

    }

    @FXML
    public void closePage() {

        router.getStage().close();

    }

    public static InputStream deserializeImage(String base64EncodedImage) {
        byte[] bytes = java.util.Base64.getDecoder().decode(base64EncodedImage);
        return new ByteArrayInputStream(bytes);
    }

}
