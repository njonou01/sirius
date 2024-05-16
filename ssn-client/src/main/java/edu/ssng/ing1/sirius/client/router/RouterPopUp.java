package edu.ssng.ing1.sirius.client.router;

import java.io.IOException;
import java.io.InputStream;

import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands;
import org.kordamp.ikonli.javafx.FontIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.business.dto.Activite;
import edu.ssng.ing1.sirius.business.dto.Activites;
import edu.ssng.ing1.sirius.client.MainClient;
import edu.ssng.ing1.sirius.client.controllers.messaging.PrivateMessagingController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RouterPopUp {
    private static RouterPopUp instance;
    private Stage stage;
    private final static String LoggingLabel = "------------------ ROUTAGE - POPup ------------------";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private static JsonNode data;
    public static Activite activite = new Activite();
    public static Activites activites = new Activites();
    private static String[] categorie = { "JeuxVideos", "Visionage", "Lecture" };
    private static String[] type = { "en ligne", "presentielle" };
    private static String[] confi = { "Public", "private" };
    public Circle circle = new Circle(50, Color.BLUE);
    private Router root;
    private static Scene scene;
    private static Stage stageReduce;
    private static VBox retrievedLayout;
    public static String currentPagePopUp = new String("createActivityPage1");
    public static ProgressBar progressBar;
    public static double MinousProgress;

    public RouterPopUp() {

        Router root = Router.getInstance();
        Stage primaryStage = root.getStage();

        stage = new Stage();
        stageReduce = new Stage();
        setReducePopUp();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(primaryStage);
        stage.initStyle(StageStyle.UNDECORATED);

        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();
        double popupWidth = screenWidth * 0.95;
        double popupHeight = screenHeight * 0.95;

        scene = new Scene(popupRoot, 6, 6);
        stage.setWidth(popupWidth);
        stage.setHeight(popupHeight);
        stage.setScene(scene);
        stage.setTitle("Create Activity");
        stage.centerOnScreen();

        // retrievedLayout = (VBox) scene.lookup("#VBoxCenter");

    }

    public static String[] getConfi() {
        return confi;
    }

    public void setReducePopUp() {

        stageReduce.initStyle(StageStyle.UNDECORATED);
        stageReduce.setResizable(false);
        stageReduce.initOwner(Router.getInstance().getStage());
        stageReduce.setAlwaysOnTop(true);
        stageReduce.setX(Screen.getPrimary().getVisualBounds().getMaxX() - 110);
        stageReduce.setY(Screen.getPrimary().getVisualBounds().getMaxY() - 58);
        stageReduce.setWidth(55);
        stageReduce.setHeight(29);

        FontIcon reduceIcon = new FontIcon(FontAwesomeBrands.ASYMMETRIK);
        reduceIcon.setIconSize(30);

        StackPane stackPane = new StackPane(reduceIcon);
        stackPane.setStyle("-fx-background-color: lightblue;");
        Scene scene = new Scene(stackPane);
        stageReduce.setScene(scene);

        stackPane.setOnMouseClicked(mouseEvent -> {

            RouterPopUp.getInstance().navigateTo(currentPagePopUp);
            // stageReduce.close();
        }

        );

    }

    public void ReducePopUp() {
        getStage().close();

        stageReduce.show();

    }

    public static RouterPopUp getInstance() {
        if (instance == null) {
            instance = new RouterPopUp();
            RouterPopUp.readRoutageJson("routagePopUp.json");
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static FXMLLoader loadFxml(String path) {
        String finalPath = "views/" + path + ".fxml";
        return new FXMLLoader(MainClient.class.getResource(finalPath));
    }

    public void navigateTo(String name) {
        currentPagePopUp = name;
        if (stageReduce.isShowing()) {
            stageReduce.close();
        }
        ;

        try {
            JsonNode node = RouterPopUp.data.get(name);
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
        InputStream inputStream = RouterPopUp.class.getResourceAsStream(file);
        ObjectMapper mapper = new ObjectMapper();
        try {
            RouterPopUp.data = mapper.readTree(inputStream);
        } catch (Exception e) {
            LoggerFactory.getLogger(RouterPopUp.class).error("error", e);
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

        if (node.get("path").asText().equals("createActivity/createActivityPage1")) {
            scene = new Scene(parent);
            scene.setRoot(parent);
            retrievedLayout = (VBox) scene.lookup("#VBoxCenter");
            progressBar = (ProgressBar) scene.lookup("#progressBar");
            MinousProgress = 0;
            progressBar.setProgress(RouterPopUp.MinousProgress);

        } else {
            // RouterPopUp.progressBar.setProgress(0.66);

            retrievedLayout.getChildren().setAll(parent);
        }

        stage.setScene(scene);
        stage.show();
        return true;
    }

    public FXMLLoader getParentNode(String name) {
        JsonNode node = RouterPopUp.data.get(name);
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

    public static String[] getCategorie() {
        return categorie;
    }

    public static String[] getType() {
        return type;
    }

    public static void setCategorie(String[] categorie) {
        RouterPopUp.categorie = categorie;
    }

}