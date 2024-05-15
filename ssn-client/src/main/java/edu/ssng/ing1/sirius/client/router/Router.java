package edu.ssng.ing1.sirius.client.router;

import java.io.IOException;
import java.io.InputStream;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.client.MainClient;

public class Router {
    private static Router instance;
    private Stage stage;
    private static final String LOGGING_LABEL = "ROUTAGE";
    private final Logger logger = LoggerFactory.getLogger(LOGGING_LABEL);
    private static JsonNode data;

    private Router() {
    }

    public synchronized static Router getInstance() {
        if (instance == null) {
            instance = new Router();
            readRoutageJson("routage.json");
        }
        return instance;
    }

    public synchronized void setStage(Stage stage) {
        this.stage = stage;
    }

    private FXMLLoader loadFxml(String path) {
        String finalPath = "views/" + path + ".fxml";
        return new FXMLLoader(MainClient.class.getResource(finalPath));
    }

    public synchronized void navigateTo(String name) {
        try {
            JsonNode node = Router.data.get(name);
            if (node == null || !displayScene(node))
                logger.error("Page {} does not exist", name);
            else
                logger.info("The {} scene was correctly loaded", name);

        } catch (IOException e) {
            logger.error("Error navigating to {}: {}", name, e.getMessage());
        }
    }

    private static void readRoutageJson(String file) {
        try (InputStream inputStream = Router.class.getResourceAsStream(file)) {
            ObjectMapper mapper = new ObjectMapper();
            Router.data = mapper.readTree(inputStream);
        } catch (IOException e) {
            // Log the error
            LoggerFactory.getLogger(Router.class).error("Error reading JSON file {}: {}", file, e.getMessage());
        }
    }

    private String getPath(JsonNode node) {
        return node.get("path").asText();
    }

    private String getTitle(JsonNode node) {
        return node.get("title").asText();
    }

    private synchronized boolean displayScene(JsonNode node) throws IOException {
        if (node == null)
            return false;
        FXMLLoader fxmlLoader = loadFxml(getPath(node));
        Parent parent = fxmlLoader.load();
        Platform.runLater(() -> {
            stage.setTitle(getTitle(node));
            stage.setScene(new Scene(parent));
            stage.show();

        });
        return true;
    }

    public synchronized FXMLLoader getParentNode(String name) {
        JsonNode node = Router.data.get(name);
        if (node == null) {
            logger.error("Parent node {} does not exist", name);
            return null;
        } else {
            logger.info("The {} node parent was correctly loaded", name);
            return loadFxml(getPath(node));
        }
    }

    public Stage getStage() {
        return this.stage;
    }

    public Scene getScene() {
        return this.stage.getScene();
    }

    public void setFullScreenStage() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
    }
}
