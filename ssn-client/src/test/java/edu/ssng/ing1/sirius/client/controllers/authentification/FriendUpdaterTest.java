package edu.ssng.ing1.sirius.client.controllers.authentification;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ssng.ing1.sirius.business.dto.BeFriend;
import edu.ssng.ing1.sirius.business.dto.BeFriends;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.controllers.commons.Initializer;
import edu.ssng.ing1.sirius.client.controllers.friend.FriendUpdater;
import edu.ssng.ing1.sirius.client.router.Router;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FriendUpdaterTest {

    @Mock
    private FlowPane acceptedFriend;

    @Mock
    private FlowPane demandedFriend;

    @Mock
    private FlowPane suggestedFriend;

    @Mock
    private FlowPane limitedDemandedFriend;

    @Mock
    private FlowPane limitedSuggestedFriend;

    @Mock
    private Initializer initializer;

    @Mock
    private Router router;

    @InjectMocks
    private FriendUpdater friendUpdater;

    @BeforeEach
    public void setup() {
        FriendUpdater.getInstance(acceptedFriend, demandedFriend, suggestedFriend, limitedDemandedFriend, limitedSuggestedFriend);
    }

    @Test
    public void testUpdateAcceptedFriend() {
        Student student = mock(Student.class);
        Student student2 = mock(Student.class);

        Set<BeFriend> friends = new HashSet<>();
        friends.add(new BeFriend(student, student2));

        BeFriend beFriend = new BeFriend();
        beFriend.setSender(student);
        when(Initializer.getFriends()).thenReturn(new HashSet<>(List.of(beFriend)));

        FriendUpdater.updateAcceptedFriend();









        
        verify(acceptedFriend).getChildren().clear();
        verify(acceptedFriend).getChildren().setAll(anyList());
    }

    @Test
    public void testUpdateDemandedFriend() {
        Student student = mock(Student.class);
        BeFriend beFriend = new BeFriend();
        beFriend.setSender(student);
        when(Initializer.friendshipRequestReceiver()).thenReturn(Set.of(beFriend)); 

        FriendUpdater.updateDemandedFriend();

        verify(demandedFriend).getChildren().clear();
        verify(demandedFriend).getChildren().setAll(anyList());
    }

    @Test
    public void testUpdateSuggestedFriend() {
        Student student = mock(Student.class);
        BeFriends beFriends = new BeFriends();
        beFriends.setStudents(new HashSet<>(List.of(student))); 

        FriendUpdater.updateSuggestedFriend();

        verify(suggestedFriend).getChildren().clear();
        verify(suggestedFriend).getChildren().setAll(anyList());
    }

    @Test
    public void testSetAcceptOnAction() {
        Button acceptBtn = new Button();
        AnchorPane pane = new AnchorPane();
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("receiver", 1);
        map.put("sender", 2);

        FriendUpdater.setAcceptOnAction(acceptBtn, demandedFriend, pane, map);

        acceptBtn.fire();

        verify(demandedFriend).getChildren().remove(pane);
    }

    @Test
    public void testSetIgnoreOnAction() {
        Button ignoreBtn = new Button();
        AnchorPane pane = new AnchorPane();
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("receiver", 1);
        map.put("sender", 2);

        FriendUpdater.setIgnoreOnAction(ignoreBtn, demandedFriend, pane, map);

        ignoreBtn.fire();

        verify(demandedFriend).getChildren().remove(pane);
    }
}
