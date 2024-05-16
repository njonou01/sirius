package edu.ssng.ing1.sirius.client;

import java.io.IOException;

import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.controllers.commons.UserInfo;
import edu.ssng.ing1.sirius.client.notificationManagement.ClientConnexion;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPopUp;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainClient extends Application {

    public static VBox notificationVBOX;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Router router = Router.getInstance();
        // router.setStage(primaryStage);
        // router.setFullScreenStage();
        // router.navigateTo("messaging");
       
        try {
            LoadingNotificationPanel();
        } catch (IOException e) {
            System.out.println("Error in loading Notification Panel :" + e.getMessage());
        }
        UserInfo.getInstance();
        Router router = Router.getInstance();
        primaryStage.setOnCloseRequest(event -> ClientConnexion.closersocket());
        router.setStage(primaryStage);
        // router.setFullScreenStage();
        // System.out.println("****************************************************");
        // System.out.println(primaryStage);
        // System.out.println("****************************************************");
        Student user = UserInfo.getUser();
        
        if (user == null)
            Platform.runLater(() -> router.navigateTo("authentification"));
        else {
            new HomeBuild();
        }
    }
    private static void LoadingNotificationPanel() throws IOException{
        FXMLLoader homeFXML =RouterPopUp.loadFxml("main/ssn-home");
        Parent parent = homeFXML.load();
        Scene scene = new Scene(parent);
        scene.setRoot(parent);
        notificationVBOX = (VBox) scene.lookup("#notificationVBOX");


        

    }

    public static void main(String[] args) {
        launch(args);
    }

}
// import javafx.application.Application;
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
// import javafx.scene.Scene;
// import javafx.scene.control.ListCell;
// import javafx.scene.control.ListView;
// import javafx.scene.layout.VBox;
// import javafx.stage.Stage;

// import java.sql.Timestamp;

// import edu.ssng.ing1.sirius.business.dto.Message;

// public class MainClient extends Application {

// @Override
// public void start(Stage primaryStage) {
// // Création des données de messages
// ObservableList<Message> messages = FXCollections.observableArrayList(
// new Message(1, 1, 2, "Message 1", null, new
// Timestamp(System.currentTimeMillis())),
// new Message(2, 1, 2, "Message 2", null, new
// Timestamp(System.currentTimeMillis())),
// new Message(3, 2, 1, "Message 3", null, new
// Timestamp(System.currentTimeMillis())));

// // Création du ListView
// ListView<Message> messageListView = new ListView<>();
// messageListView.setItems(messages);

// // Configuration du modèle de cellule personnalisé
// messageListView.setCellFactory(param -> new ListCell<Message>() {
// @Override
// protected void updateItem(Message message, boolean empty) {
// super.updateItem(message, empty);
// if (empty || message == null) {
// setText(null);
// } else {
// setText("De: " + message.getSenderId() + ", Pour: " + message.getReceiverId()
// +
// ", Message: " + message.getMessageText() + ", Envoyé à: " +
// message.getSentAt());
// }
// }
// });

// // Création de la scène
// VBox root = new VBox(messageListView);
// Scene scene = new Scene(root, 400, 300);

// // Configuration de la scène principale
// primaryStage.setScene(scene);
// primaryStage.setTitle("Liste de Messages");
// primaryStage.show();
// }

// public static void main(String[] args) {
// launch(args);
// }
// }
