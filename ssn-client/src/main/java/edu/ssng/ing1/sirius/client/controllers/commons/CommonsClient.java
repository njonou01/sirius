package edu.ssng.ing1.sirius.client.controllers.commons;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class CommonsClient {
     public static void setclipOnImage(ImageView imageView) {
        Image image = imageView.getImage();
        Rectangle clip = new Rectangle(imageView.getFitWidth(),
                imageView.getFitHeight());
        clip.setArcWidth(image.getWidth());
        clip.setArcHeight(image.getHeight());
        imageView.setClip(clip);
    }
}
