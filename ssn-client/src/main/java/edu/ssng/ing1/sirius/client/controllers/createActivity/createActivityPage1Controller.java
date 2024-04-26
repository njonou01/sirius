package edu.ssng.ing1.sirius.client.controllers.createActivity;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import edu.ssng.ing1.sirius.client.controllers.authentification.Rules;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPopUp;
import edu.ssng.ing1.sirius.client.toast.Toast;
import edu.ssng.ing1.sirius.client.toast.ToastType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * createActivityPage2Controller
 */
public class createActivityPage1Controller implements Initializable {
    private RouterPopUp router;

    Font customFont2 = Font.loadFont(getClass().getResourceAsStream(
            "../../../../../../../../resources/edu/ssng/ing1/sirius/client/views/asset/font/Roboto/Roboto-Italic.ttf"),
            30);

    @FXML
    private TextField categorieActivityField;

    @FXML
    private FontIcon reduce;

    @FXML
    private FontIcon backBtn;

    @FXML
    private TextField nameActivityField;

    @FXML
    private Label label;

    @FXML
    private ChoiceBox choiceCategorie;

    @FXML
    private ChoiceBox choiceType;

    @FXML
    private BorderPane borderPaneRoot;

    @FXML
    private Button nextButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private VBox VBoxCenter;

    // @FXML
    // private BorderPane borderPaneActivity;

    private int maxLength = 20;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backBtn.setVisible(false);
        progressBar.setProgress(0);
        router = RouterPopUp.getInstance();
        nameActivityField.setPromptText("Entrez le nom de votre activité");
        // borderPaneRoot.getStyleClass().add("popup-window");

        // borderPaneActivity.getStylesheets().add(getClass().getResource("addActivity.css").toExternalForm());
        choiceCategorie.getItems().addAll(RouterPopUp.getCategorie());
        choiceType.getItems().addAll(RouterPopUp.getType());
        choiceCategorie.getSelectionModel().selectFirst();
        choiceType.getSelectionModel().selectFirst();
        nameActivityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > maxLength) {
                nameActivityField.setText(oldValue);
            }

            if (!newValue.matches("[a-zA-Z0-9]*")) {
                nameActivityField.setStyle("-fx-background-color: #FFCCCC;");
                //   ,nameActivityField.rrsetStyle("-fx-text-fill: red;");
                label.setText("Les caractères spéciaux ne sont pas autorisés");
                // label.setStyle("-fx-text-fill: red;");
            } else {
                nameActivityField.setStyle("");
                // label.setText("");
            }

        });

    }

    @FXML
    public void nextPage() {

        if (nameActivityField.getText().matches("[a-zA-Z0-9]*") && !nameActivityField.getText().isEmpty()) {
            RouterPopUp.activite.setCategorie((String) choiceCategorie.getValue());
            RouterPopUp.activite.setNom_interet_activite(nameActivityField.getText());
            System.out.println(RouterPopUp.activite);
            backBtn.setVisible(true);
            RouterPopUp.MinousProgress += 0.33;
            RouterPopUp.progressBar.setProgress(RouterPopUp.MinousProgress);
            router.navigateTo("createActivityPage2");

        } else {
            System.out.println("IIIIIIIIIIIIII");
            if (nameActivityField.getText().isEmpty()) {
                Toast.buildToast(ToastType.WARNING, "Le champ \"Nom activité\" ne doit pas etre vide");

            } else {

                Toast.buildToast(ToastType.ERROR, "Pas de caractere special dans le champ \"Nom activité\" ");

            }

        }

    }

    @FXML
    public void closePage() {

        router.getStage().close();

    }

    @FXML
    public void PopUpreduce() {
        router.ReducePopUp();

    }

    @FXML
    public void backPage() {

        RouterPopUp.MinousProgress -= 0.33;
        RouterPopUp.progressBar.setProgress(RouterPopUp.MinousProgress);
        if (RouterPopUp.currentPagePopUp.equals("createActivityPage2")) {
            router.navigateTo("createActivityPage1");
        } else if (RouterPopUp.currentPagePopUp.equals("createActivityPage3")) {
            router.navigateTo("createActivityPage2");
        } else {
            router.navigateTo("createActivityPage3");
        }

    }

}