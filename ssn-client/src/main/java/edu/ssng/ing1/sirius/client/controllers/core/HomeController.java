package edu.ssng.ing1.sirius.client.controllers.core;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import edu.ssng.ing1.sirius.client.MainClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;

public class HomeController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ScrollPane homeScrollPane;

    @FXML
    private ScrollPane postScroolPane;
        private final Logger logger = LoggerFactory.getLogger("LoggingLabel");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String ssnemailpref = "SSN_USER_EMAIL";
        logger.debug("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        Preferences prefs = Preferences.userRoot().node(MainClient.class.getName());
        String userEmail = prefs.get(ssnemailpref, null);
        if (userEmail != null) {
            System.out.println(userEmail);
        logger.debug("hummmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
        }
        
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

    }

}
