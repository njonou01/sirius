package edu.ssng.ing1.sirius.client.controllers.createActivity;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import org.kordamp.ikonli.javafx.FontIcon;

import edu.ssng.ing1.sirius.client.controllers.authentification.Rules;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPoUp;
import edu.ssng.ing1.sirius.client.toast.Toast;
import edu.ssng.ing1.sirius.client.toast.ToastType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

/**
 * createActivityPage2Controller
 */
public class createActivityPage1Controller implements Initializable {
    private RouterPoUp router;

    Font customFont2 = Font.loadFont(getClass().getResourceAsStream("../../../../../../../../resources/edu/ssng/ing1/sirius/client/views/asset/font/Roboto/Roboto-Italic.ttf"), 30);

    @FXML
    private TextField categorieActivityField;

    @FXML
    private FontIcon reduce;

    @FXML
    private TextField nameActivityField;

    @FXML
    private Label label;

    @FXML
    private ChoiceBox choiceCategorie;

    // @FXML
    // private BorderPane borderPaneActivity;

    private int maxLength = 20;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        router = RouterPoUp.getInstance();
        
        // borderPaneActivity.getStylesheets().add(getClass().getResource("addActivity.css").toExternalForm());
        choiceCategorie.getItems().addAll(RouterPoUp.getCategorie());
        nameActivityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > maxLength) {
                nameActivityField.setText(oldValue);
            }

            if (!newValue.matches("[a-zA-Z0-9]*")) {
                nameActivityField.setStyle("-fx-background-color: #FFCCCC;");
                // nameActivityField.setStyle("-fx-text-fill: red;");
                label.setText("Les caractères spéciaux ne sont pas autorisés");
                // label.setStyle("-fx-text-fill: red;");
            } else {
                nameActivityField.setStyle("");
                label.setText("");
            }

        });

    }

    @FXML
    public void nextPage() {

        if (nameActivityField.getText().matches("[a-zA-Z0-9]*") && !nameActivityField.getText().isEmpty()) {
            RouterPoUp.activite.setCategorie((String) choiceCategorie.getValue());
            RouterPoUp.activite.setNom_interet_activite(nameActivityField.getText());
            System.out.println(RouterPoUp.activite);
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

}