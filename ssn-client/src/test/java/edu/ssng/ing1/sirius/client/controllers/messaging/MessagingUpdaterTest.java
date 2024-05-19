package edu.ssng.ing1.sirius.client.controllers.messaging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import edu.ssng.ing1.sirius.business.dto.BeFriend;
import edu.ssng.ing1.sirius.business.dto.Message;
import edu.ssng.ing1.sirius.business.dto.Student;
import javafx.scene.layout.VBox;


import static org.mockito.Mockito.*;

import javafx.scene.layout.VBox;
import edu.ssng.ing1.sirius.business.dto.BeFriend;
import edu.ssng.ing1.sirius.business.dto.Message;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.controllers.commons.Initializer;

import java.util.Arrays;
import java.util.HashSet;

@ExtendWith(MockitoExtension.class)
public class MessagingUpdaterTest {
    private MessagingUpdater messagingUpdater;
    private StudentBtnAction action;
    private VBox studentList;
    private VBox messageArea;

    @BeforeEach
    public void setUp() {
        action = mock(StudentBtnAction.class);
        studentList = new VBox();
        messageArea = new VBox();
        messagingUpdater = MessagingUpdater.getInstance(action, studentList, messageArea);
    }

    @Test
    public void testGetInstance() {
        assertNotNull(messagingUpdater);
    }

    @Test
    public void testGetStudentList() {
        assertNotNull(MessagingUpdater.getStudentList());
    }

    @Test
    public void testUpdateStudentList() {
        BeFriend friend = mock(BeFriend.class);
        when(Initializer.getFriends()).thenReturn(new HashSet<>(Arrays.asList(friend)));
        MessagingUpdater.updateStudentList();
        assertFalse(MessagingUpdater.getStudentList().getChildren().isEmpty());
    }

    @Test
    public void testUpdateStudentIsOnline() {
        String email = "njonougaby45@gmail.com";
        FriendBtn friendBtn = new FriendBtn(new Student(), false);
        studentList.getChildren().add(friendBtn);
        MessagingUpdater.updateStudentIsOnline(email, true);
        assertTrue(friendBtn.getIsOnline());
    }

    @Test
    public void testUpdateMesageArea() {
        Student student = new Student();
        student.setId_student(1);
        Message message = mock(Message.class);
        when(Initializer.getMessagesOfStudent(student.getId_student())).thenReturn(Arrays.asList(message));
        MessagingUpdater.updateMesageArea(student);
        assertFalse(messageArea.getChildren().isEmpty());
    }

    @Test
    public void testAddMessageInArea() {
        Message message = mock(Message.class);
        MessagingUpdater.addMessageInArea(message);
        assertFalse(messageArea.getChildren().isEmpty());
    }
}