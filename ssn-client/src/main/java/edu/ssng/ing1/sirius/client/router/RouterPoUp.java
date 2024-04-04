package edu.ssng.ing1.sirius.client.router;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.business.dto.Activite;
import edu.ssng.ing1.sirius.business.dto.Activites;
import edu.ssng.ing1.sirius.client.MainClient;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RouterPoUp {
    private static RouterPoUp instance;
    private Stage stage;
    private final static String LoggingLabel = "◅◅◅◅◅◅◅◅ ROUTAGE - POPup ▻▻▻▻▻▻▻▻";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private static JsonNode data;
    public static Activite activite = new Activite();
    public static Activites activites = new Activites();

    private RouterPoUp() {
        Router root = Router.getInstance();
        Stage primaryStage = root.getStage();

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(primaryStage);
        stage.initStyle(StageStyle.UNDECORATED);

        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        double popupWidth = screenWidth * 0.95;
        double popupHeight = screenHeight * 0.95;

        Scene popupScene = new Scene(popupRoot, 6, 6);
        stage.setWidth(popupWidth);
        stage.setHeight(popupHeight);
        stage.setScene(popupScene);
        stage.setTitle("Create Activity");
        stage.centerOnScreen();

    }

    public static RouterPoUp getInstance() {
        if (instance == null) {
            instance = new RouterPoUp();
            RouterPoUp.readRoutageJson("routagePopUp.json");
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private FXMLLoader loadFxml(String path) {
        String finalPath = "views/" + path + ".fxml";
        return new FXMLLoader(MainClient.class.getResource(finalPath));
    }

    public void navigateTo(String name) {

        try {
            JsonNode node = RouterPoUp.data.get(name);
            if (!displayScene(node))
                logger.error("Page {} does not exist", name);
            else
                logger.info("the {} scene was correctly loaded", name);

        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            e.printStackTrace();
        }

    }

    private static void readRoutageJson(String file) {
        InputStream inputStream = RouterPoUp.class.getResourceAsStream(file);
        ObjectMapper mapper = new ObjectMapper();
        try {
            RouterPoUp.data = mapper.readTree(inputStream);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    private String getPath(JsonNode node) {
        return node.get("path").asText();
    }

    private String getTile(JsonNode node) {
        return node.get("title").asText();
    }

    private boolean displayScene(JsonNode node) throws IOException {
        if (node.isNull())
            return false;
        FXMLLoader fxmlLoader = loadFxml(getPath(node));
        Parent parent = fxmlLoader.load();
        stage.setTitle(getTile(node));
        stage.setScene(new Scene(parent));

        stage.show();
        return true;
    }

    public FXMLLoader getParentNode(String name) {
        JsonNode node = RouterPoUp.data.get(name);
        try {
            if (node.isNull()) {
                logger.error("parent nod {} does not exist", name);
                return null;
            } else {
                logger.info("the {} node parent was correctly loaded", name);
                FXMLLoader fxmlLoader = loadFxml(getPath(node));
                return fxmlLoader;
            }

        } catch (Exception e) {
            logger.error("{} merde", e.getMessage());
            return null;
        }

    }

    public Stage getStage() {
        return this.stage;
    }

    public Scene getScene() {
        return this.stage.getScene();
    }

}