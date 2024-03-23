package edu.ssng.ing1.sirius.client.controllers.core;

import java.net.URL;
import java.util.ResourceBundle;

import edu.ssng.ing1.sirius.client.router.Router;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;

public class HomeController implements Initializable{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ScrollPane homeScrollPane;

    @FXML
    private ScrollPane postScroolPane;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Router router = Router.getInstance();


        // scene.addEventFilter(ScrollEvent.ANY, event -> {
        //      if (event.getTarget() == scene || event.getTarget() == postScroolPane) {
        //          double deltaY = event.getDeltaY();
        //          double currentVvalue = postScroolPane.getVvalue();
        //          double newVvalue = currentVvalue - deltaY / postScroolPane.getContent().getBoundsInLocal().getHeight();
        //          postScroolPane.setVvalue(Math.max(0, Math.min(1, newVvalue)));
        //          event.consume();
        //      }
        //  });
        
    }

    

}
