package edu.ssng.ing1.sirius.client.controllers.messaging;

import edu.ssng.ing1.sirius.business.dto.BeFriend;
import edu.ssng.ing1.sirius.business.dto.Message;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.controllers.commons.Initializer;
import javafx.scene.layout.VBox;

public class MessagingUpdater {
    private static MessagingUpdater instance;
    private static VBox studentList;
    private static StudentBtnAction action;
    private static VBox messageArea;

    private MessagingUpdater(StudentBtnAction action, VBox studentList, VBox messageArea) {
        MessagingUpdater.studentList = studentList;
        MessagingUpdater.action = action;
        MessagingUpdater.messageArea = messageArea;
    }

    public static MessagingUpdater getInstance(StudentBtnAction action, VBox studentList, VBox messageArea) {
        if (instance == null) {
            instance = new MessagingUpdater(action, studentList, messageArea);
            System.out.println("MessagingUpdater instance created");
        }
        return instance;
    }

    public static VBox getStudentList() {
        if (studentList == null) {
            throw new NullPointerException("studentList is null");
        }
        return studentList;
    }

    public static void updateStudentList() {
        studentList.getChildren().clear();
        for (BeFriend friend : Initializer.getFriends()) {
            FriendBtn friendBtn = new FriendBtn(friend.getSender(), false);
            studentList.getChildren().add(friendBtn);
            friendBtn.getButton().setOnAction(e -> action.setEventStudentActive(friendBtn));
        }
    }

    public static void updateStudentIsOnline(String email, boolean isOnline) {
        studentList.getChildren().forEach(studentUi -> {
            FriendBtn friendBtn = (FriendBtn) studentUi;
            if (friendBtn.getStudent().getEmail() == email) {
                friendBtn.setIsOnline(isOnline);
                return;
            }
        });
    }

    public static void updateMesageArea(Student student ) {
        messageArea.getChildren().clear();
        Initializer.getAllMessages().stream().map(null);
        // messageArea.getChildren().add(new Label(message));

    }

    public static void addMessageInArea(Message msg) {
        messageArea.getChildren().add(new PrivateMessage(msg));
    }

}
