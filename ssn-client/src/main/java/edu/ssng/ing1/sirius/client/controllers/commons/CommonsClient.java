package edu.ssng.ing1.sirius.client.controllers.commons;

import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class CommonsClient {
    public static String logoApppath = "media/images/ssn-logo.png"; 
    public static void setclipOnImage(ImageView imageView) {
        Image image = imageView.getImage();
        Rectangle clip = new Rectangle(imageView.getFitWidth(),
                imageView.getFitHeight());
        clip.setArcWidth(image.getWidth());
        clip.setArcHeight(image.getHeight());
        imageView.setClip(clip);
    }
    public static void setclipOnImage(ImageView imageView , String path) {
        Image image = getImage(path);
        Rectangle clip = new Rectangle(imageView.getFitWidth(),
                imageView.getFitHeight());
        clip.setArcWidth(image.getWidth());
        clip.setArcHeight(image.getHeight());
        imageView.setClip(clip);
    }

    public static Image getImage(String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        return new Image(inputStream);
    }

    public static void setImageOnClip(Circle profileimageClip, String path) {
        Image profilImage = getImage(path);
        profileimageClip.setFill(new ImagePattern(profilImage));
        profileimageClip.setStroke(Color.TRANSPARENT);
    }

}
