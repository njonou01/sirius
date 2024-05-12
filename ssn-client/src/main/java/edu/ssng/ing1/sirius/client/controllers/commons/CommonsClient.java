package edu.ssng.ing1.sirius.client.controllers.commons;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public static void setclipOnImage(ImageView imageView, String path) {
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

    public static String durationOfEvent(Timestamp timestamp) {
        Instant now = Instant.now();
        Instant then = timestamp.toInstant();
        Duration duration = Duration.between(then, now);

        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        long days = absSeconds / 86400;
        long months = days / 30;
        long hours = (absSeconds % 86400) / 3600;
        long minutes = (absSeconds % 3600) / 60;
        long secs = absSeconds % 60;

        if (months >= 12) {
            long years = months / 12;
            return years + " an" + (years == 1 ? "" : "s");
        } else if (months >= 1) {
            return months + " mois";
        } else if (days >= 1) {
            return days + " jour(s)" + (days == 1 ? "" : "s");
        } else if (hours >= 1) {
            return hours + " h";
        } else if (minutes >= 1) {
            return minutes + " min";
        } else {
            return secs + " s";
        }
    }

    public static String dateOfEvent(Timestamp timestamp) {
        LocalDateTime dateTime = timestamp.toLocalDateTime();

        LocalDate today = LocalDate.now();

        LocalDate messageDate = dateTime.toLocalDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        if (messageDate.equals(today.minusDays(1))) {
            return "Hier à " + dateTime.format(formatter);
        } else if (messageDate.equals(today.minusDays(2))) {
            return "Avant-hier à " + dateTime.format(formatter);
        } else {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return dateTime.format(formatter);
        }
    }

    public static Image getImage(byte[] byteArray) {
        return new Image(convertBytesToInputStream(byteArray));
    }

    public  static InputStream convertBytesToInputStream(byte[] byteArray) {
        return new ByteArrayInputStream(byteArray);
    }

}
