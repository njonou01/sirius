package edu.ssng.ing1.sirius.client.controllers.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

import edu.ssng.ing1.sirius.client.MainClient;
import edu.ssng.ing1.sirius.client.router.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private MenuItem deconnexionbtn;

    @FXML
    private ImageView profileImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deconnexionbtn.setOnAction(event -> {
            String ssnemailpref = "SSN_USER_EMAIL";
            Preferences prefs = Preferences.userRoot().node(MainClient.class.getName());
            prefs.remove(ssnemailpref);
            Router.getInstance().navigateTo("authentification");
            Router.getInstance().getStage().sizeToScene();
        });
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("media/images/ssn-logo.png");
        profileImage.setImage(new Image(inputStream));
        try {
            PreloadRequest.getInstance().getAllFriends().getBefriends().stream()
                    .filter(friend -> "accepted".equals(friend.getStatus()))
                    .collect(Collectors.toList());
        } catch (NullPointerException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        createActivityBtn.setOnAction(event -> {
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

            corePane.setCenter(InterfaceInitilizer.getFriendPane());

        });
        homePageBtn.setOnAction(event -> {
            try {
                corePane.setCenter(homePane);
            } catch (Exception e) {
                System.out.println(e);
            }
        });

    }

}
