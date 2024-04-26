package edu.ssng.ing1.sirius.client.controllers.commons;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.client.router.RouterPopUp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class HomeController implements Initializable {
    HashMap<Button, BorderPane> btnmapper = new HashMap<Button, BorderPane>();

    @FXML
    private Button seeActivitybtn;
    @FXML
    private ImageView logo;
    @FXML
    private BorderPane corePane;

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
    private BorderPane homePane;

    @FXML
    private ScrollPane postScroolPane;

    @FXML
    private ImageView profileImage;

    @FXML
    private ImageView profileImage2;

    Router router = Router.getInstance();
    RouterPopUp routerPoUp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RouterPopUp routerPoUp=RouterPopUp.getInstance();
        logo.setImage(getImage("media/images/ssn-logo.png"));
        Student user = UserInfo.getUser();
        if (user != null) {
            profileImage.setImage(getImage(user.getProfileImageStream()));
            profileImage2.setImage(getImage(user.getProfileImageStream()));

            btnmapper.put(homePageBtn, homePane);
            BorderPane invitation = Initializer.initInvitationPage();
            invitation.setVisible(false);
            Appstack.getChildren().add(invitation);
            btnmapper.put(friendPageBtn, invitation);
            friendPageBtn.setOnAction(event -> {
                System.out.println("hello");
            });

        }
        initializeBtn(homePageBtn, friendPageBtn);

        deconnexionbtn.setOnAction(event -> {
            UserInfo.removeUser();
            Router.getInstance().navigateTo("authentification");
            Router.getInstance().getStage().sizeToScene();
            Router.getInstance().setFullScreenStage();
        });
        createActivityBtn.setOnAction(event -> {
            routerPoUp.navigateTo("createActivityPage1");
        });

    }

    private Image getImage(byte[] byteArray) {
        return new Image(convertBytesToInputStream(byteArray));
    }

    private Image getImage(String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        return new Image(inputStream);
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
