package edu.ssng.ing1.sirius.client.router;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.client.MainClient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Router {
    private static Router instance;
    private Stage stage;
    private final static String LoggingLabel = "ROUTAGE";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private static JsonNode data;

    private Router() {
    }

    public static Router getInstance() {
        if (instance == null) {
            instance = new Router();
            Router.readRoutageJson("routage.json");
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
            JsonNode node = Router.data.get(name);
            if (!displayScene(node))
                logger.error("Page {} does not exist", name);
            else
                logger.info("the {} scene was correctly loaded", name);

        } catch (Exception e) {
            logger.error("{}", e.getMessage());
        }

    }

    private static void readRoutageJson(String file) {
        InputStream inputStream = Router.class.getResourceAsStream(file);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Router.data = mapper.readTree(inputStream);
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
        JsonNode node = Router.data.get(name);
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
        return stage;
    }

}