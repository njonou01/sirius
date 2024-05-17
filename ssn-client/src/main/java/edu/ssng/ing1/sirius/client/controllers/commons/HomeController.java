package edu.ssng.ing1.sirius.client.controllers.commons;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.notificationManagement.ClientConnexion;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPopUp;
import edu.ssng.ing1.sirius.commons.Notification;
import edu.ssng.ing1.sirius.requests.Disconnection.Disconnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.layout.VBox;

public class HomeController implements Initializable {
    private static HomeController instance;
    private static Logger logger = LoggerFactory.getLogger(HomeController.class);

    public static HomeController getinstance() {
        if (instance == null) {
            instance = new HomeController();
            logger.info("HomeController instance created");
        }
        return instance;
    }

    HashMap<Button, BorderPane> btnmapper = new HashMap<Button, BorderPane>();
    @FXML
    private Button seeActivitybtn;
    @FXML
    private ImageView logo;
    @FXML
    private BorderPane corePane;
    @FXML
    private Circle profileimageClip;

    @FXML
    private Button createActivityBtn;

    @FXML
    private StackPane Appstack;

    @FXML
    private MenuItem deconnexionbtn;

    @FXML
    private Button friendPageBtn;

    @FXML
    private Button homePageBtn;
    @FXML
    private Button messagingBtn;
    @FXML
    private BorderPane homePane;

    @FXML
    private ScrollPane postScroolPane;

    @FXML
    private ImageView profileImage2;

    @FXML
    private AnchorPane AnchorPaneNotif;

    public static VBox vBoxN;

    Router router = Router.getInstance();

    RouterPopUp routerPoUp;

    public static Boolean passage=false;

    private static Boolean isAlreadyDisplay = false;

    private static Set<Notification> notificationToBedisplayed = new HashSet<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        RouterPopUp routerPoUp = RouterPopUp.getInstance();
        logo.setImage(getImage("media/images/ssn-logo.png"));
        Student user = UserInfo.getUser();

        getPanenotif();


        System.out.println(vBoxN);
        displayOnnotifPanel();
        isAlreadyDisplay = true;
        if (user != null && passage !=true) {
            Image profilImage = getImage(user.getProfileImageStream());
            profileimageClip.setFill(new ImagePattern(profilImage));
            profileimageClip.setStroke(Color.TRANSPARENT);

            profileImage2.setImage(getImage(user.getProfileImageStream()));
            CommonsClient.setclipOnImage(profileImage2);
            btnmapper.put(homePageBtn, homePane);
            BorderPane invitation = Initializer.initInvitationPage();
            BorderPane messaging = Initializer.initMessagingPage();
            messaging.setVisible(false);
            invitation.setVisible(false);
            Appstack.getChildren().addAll(invitation, messaging);
            btnmapper.put(friendPageBtn, invitation);
            btnmapper.put(homePageBtn, homePane);
            btnmapper.put(messagingBtn, messaging);

        }
        initializeBtn(homePageBtn, friendPageBtn, messagingBtn);

        deconnexionbtn.setOnAction(event -> {
            Student student = UserInfo.getUser();
            UserInfo.removeUser();
            ClientConnexion.closersocket();

            try {
            Disconnection.disconnection(student.getEmail());
            } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
            Router.getInstance().navigateTo("authentification");
            Router.getInstance().getStage().sizeToScene();
            // Router.getInstance().setFullScreenStage();
        });
        createActivityBtn.setOnAction(event -> {
            routerPoUp.navigateTo("createActivityPage1");
        });

    }

    private Image getImage(byte[] byteArray) {
        return new Image(convertBytesToInputStream(byteArray));
    }

    public synchronized static void displayOnnotifPanel() {

        Platform.runLater(() -> {
            for (Notification notification : notificationToBedisplayed) {
                if (isAlreadyDisplay && notificationToBedisplayed.contains(notification)) {
                    // System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                    // System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                    // System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                    // System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                    continue;
                }

                Label label = new Label(notification.getMessage()+" "+ notification.getHoursReceive());
                label.setWrapText(true);
                final HBox hbox = new HBox();
                hbox.getChildren().addAll(label);
                vBoxN.getChildren().add(hbox);
            }

        });

    }

    public void getPanenotif() {
        vBoxN = (VBox) AnchorPaneNotif.lookup("#vBoxNotif");
    }

    private Image getImage(String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        return new Image(inputStream);
    }

    public static Set<Notification> getNotificationToBedisplayed() {
        return notificationToBedisplayed;
    }

    public static void setNotificationToBedisplayed(Set<Notification> notificationToBedisplayed) {
        HomeController.notificationToBedisplayed = notificationToBedisplayed;
    }

    private static InputStream convertBytesToInputStream(byte[] byteArray) {
        return new ByteArrayInputStream(byteArray);
    }

    public void initializeBtn(Button... btnsListf) {
        for (Button button : btnsListf) {
            button.setOnAction(event -> {
                System.out.println(button);
                for (BorderPane entry : this.btnmapper.values()) {
                    entry.setVisible(false);
                }
                btnmapper.get(button).setVisible(true);
            });
        }
    }

    @FXML
    public void seeActivity() {
        router.navigateTo("activityCreated");
    }

    @FXML
    public void seeMyActivity() {
        router.navigateTo("seeMyActivity");
    }

    @FXML
    public void closePage() {
        router.getStage().close();
    }

}
