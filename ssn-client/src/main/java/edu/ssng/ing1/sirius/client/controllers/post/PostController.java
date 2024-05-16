package edu.ssng.ing1.sirius.client.controllers.post;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PostController {
    @FXML
    private ImageView imagePost;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button closePost;

    @FXML
    private Button followBtn;

    @FXML
    private AnchorPane imageBlockPost;

    @FXML
    private Text postName;

    @FXML
    private Text postPublicationDate;

    @FXML
    private Text schoolPost;

    @FXML
    private Text textPost;

    @FXML
    private MediaView videoPost;

    @FXML
    private ImageView profilUserPost;

    @FXML
    private ImageView commentUserProfileImg;

    @FXML
    void initialize() {
        closePost.setOnAction(event->{
            
        });
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("media/images/test.jpg");

        if (inputStream != null) {
            //To do 
        } else {
           // todo
        }
        imagePost.setImage(new Image(inputStream));
        inputStream = classLoader.getResourceAsStream("media/images/profil.jpg");
        Image image = new Image(inputStream);
        profilUserPost.setImage(image);

        Rectangle clip = new Rectangle(profilUserPost.getFitWidth(),
                profilUserPost.getFitHeight());
        clip.setArcWidth(image.getWidth());
        clip.setArcHeight(image.getHeight());
        profilUserPost.setClip(clip);

        inputStream = classLoader.getResourceAsStream("media/images/profil.jpg");
        Image image2 = new Image(inputStream);
        commentUserProfileImg.setImage(image);

        Rectangle clip1 = new Rectangle(commentUserProfileImg.getFitWidth(),
                commentUserProfileImg.getFitHeight());
        clip1.setArcWidth(image2.getWidth());
        clip1.setArcHeight(image2.getHeight());
        commentUserProfileImg.setClip(clip1);




    }

}
