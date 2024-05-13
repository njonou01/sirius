package edu.ssng.ing1.sirius.client.controllers.messaging;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class ImageFileChooser {

    private final FileChooser fileChooser;

    public ImageFileChooser() {
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter(
                "Images", "*.png", "*.jpeg", "*.jpg");
        fileChooser.getExtensionFilters().add(imageFilter);
    }

    public File showOpenDialog(Stage primaryStage) {
        return fileChooser.showOpenDialog(primaryStage);
    }
}