package edu.ssng.ing1.sirius.client.controllers.messaging;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import edu.ssng.ing1.sirius.business.dto.Message;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.controllers.commons.CommonsClient;
import edu.ssng.ing1.sirius.client.controllers.commons.Initializer;
import edu.ssng.ing1.sirius.client.controllers.commons.UserInfo;
import edu.ssng.ing1.sirius.client.router.Router;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PrivateMessage extends HBox {
    private Student me = UserInfo.getUser();
    private final double MAX_HEIGHT = 533;

    public PrivateMessage(Message message) {
        super();
        String whichMessage = me.getId_student() == message.getSenderId()
                ? "my-messageUI"
                : "friend-messageUI";
        try {
            FXMLLoader loader = Router.getInstance().getParentNode(whichMessage);
            Parent root = loader.load();
            PrivateMessageControler controller = loader.getController();
            updateSizeOfImage(
                    message.getMedia() == null ? null : new Image(new ByteArrayInputStream(message.getMedia())),
                    controller.getImage());
            controller.getMessage().setText(message.getMessageText());
            controller.getSendingDate().setText(CommonsClient.dateOfEvent(message.getSentAt()));
            this.getChildren().setAll(root);
            if (message.getSenderId() == me.getId_student()) {
                controller.getProfileImage().setImage(CommonsClient.getImage(me.getProfileImageStream()));
            } else {
                Student friend = getFriendInfo(message.getSenderId() == me.getId_student()
                        ? message.getReceiverId()
                        : message.getSenderId());
                controller.getProfileImage().setImage(CommonsClient.getImage(friend.getProfileImageStream()));
            }

        } catch (IOException e) {

        }
    }

    private Student getFriendInfo(int id_student) {
        Student[] friendInfo = new Student[1];
        Initializer.getFriends().stream()
                .filter(f -> f.getSender().getId_student() == id_student
                        || f.getReceiver().getId_student() == id_student)
                .findFirst()
                .ifPresent(f -> {
                    System.out.println(f.getSender().getFirstname());
                    friendInfo[0] = f.getSender().getId_student() == me.getId_student() ? f.getReceiver()
                            : f.getSender();
                });
        return friendInfo[0];
    }

    private void updateSizeOfImage(Image image, ImageView imageView) {
        if (image == null) {
            VBox Parent = (VBox) imageView.getParent();
            Parent.getChildren().remove(imageView);
        }

        else {
            double width = image.getWidth();
            double height = image.getHeight();
            if (height > MAX_HEIGHT) {
                double ratio = MAX_HEIGHT / height;
                height *= ratio;
                width *= ratio;
            }

            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            imageView.setImage(image);
        }

    }
}
