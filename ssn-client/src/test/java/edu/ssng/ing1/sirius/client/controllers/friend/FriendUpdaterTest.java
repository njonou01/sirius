package edu.ssng.ing1.sirius.client.controllers.friend;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.ssng.ing1.sirius.business.dto.BeFriend;
import edu.ssng.ing1.sirius.business.dto.BeFriends;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.business.dto.Students;
import edu.ssng.ing1.sirius.client.controllers.commons.Initializer;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

@ExtendWith(MockitoExtension.class)
public class FriendUpdaterTest {

    @Mock
    private FlowPane mockAcceptedFriend;

    @Mock
    private FlowPane mockDemandedFriend;

    @Mock
    private FlowPane mockSuggestedFriend;

    @Mock
    private FlowPane mockLimitedDemandedFriend;

    @Mock
    private FlowPane mockLimitedSuggestedFriend;

    @Mock
    private BeFriends mockBeFriends;

    @Mock
    private Students mockStudents;

    @Mock
    private BeFriend mockBeFriend;

    @Mock
    private Student mockStudent;

    @BeforeEach
    public void setUp() {
        when(mockBeFriends.getBefriends()).thenReturn(Set.of(mockBeFriend));
        when(mockBeFriend.getSender()).thenReturn(mockStudent);
        when(mockBeFriend.getStatus()).thenReturn("accepted");
        when(mockStudents.getStudents()).thenReturn(Set.of(mockStudent));
    }

    @Test
    public void testUpdateAcceptedFriend() {
        try (MockedStatic<Initializer> mockedInitializer = Mockito.mockStatic(Initializer.class)) {
            mockedInitializer.when(Initializer::getFriends).thenReturn(mockBeFriends.getBefriends());
            System.out.println("Initialiser crrer ");
            FriendUpdater instance = FriendUpdater.getInstance(
                    mockAcceptedFriend,
                    mockDemandedFriend,
                    mockSuggestedFriend,
                    mockLimitedDemandedFriend,
                    mockLimitedSuggestedFriend);

            FriendUpdater.updateAcceptedFriend();

            verify(mockAcceptedFriend).getChildren().clear();
            verify(mockAcceptedFriend).getChildren().setAll(anyList());

            System.out.println("verif");
        }
    }

    @Test
    public void testUpdateDemandedFriend() {
        try (MockedStatic<Initializer> mockedInitializer = Mockito.mockStatic(Initializer.class)) {
            mockedInitializer.when(Initializer::friendshipRequestReceiver).thenReturn(mockBeFriends.getBefriends());

            FriendUpdater instance = FriendUpdater.getInstance(
                    mockAcceptedFriend,
                    mockDemandedFriend,
                    mockSuggestedFriend,
                    mockLimitedDemandedFriend,
                    mockLimitedSuggestedFriend);









            





            FriendUpdater.updateDemandedFriend();

            verify(mockDemandedFriend).getChildren().clear();
            verify(mockDemandedFriend).getChildren().setAll(anyList());
        }
    }

    @Test
    public void testUpdateSuggestedFriend() {
        try (MockedStatic<Initializer> mockedInitializer = Mockito.mockStatic(Initializer.class)) {
            mockedInitializer.when(Initializer::getSuggestions).thenReturn(mockStudents);

            FriendUpdater instance = FriendUpdater.getInstance(
                    mockAcceptedFriend,
                    mockDemandedFriend,
                    mockSuggestedFriend,
                    mockLimitedDemandedFriend,
                    mockLimitedSuggestedFriend);

            FriendUpdater.updateSuggestedFriend();

            verify(mockSuggestedFriend).getChildren().clear();
            verify(mockSuggestedFriend).getChildren().setAll(anyList());
        }
    }

    @Test
    public void testUpdateLimitedSuggestedFriend() {
        try (MockedStatic<Initializer> mockedInitializer = Mockito.mockStatic(Initializer.class)) {
            mockedInitializer.when(Initializer::getSuggestions).thenReturn(mockStudents);

            FriendUpdater instance = FriendUpdater.getInstance(
                    mockAcceptedFriend,
                    mockDemandedFriend,
                    mockSuggestedFriend,
                    mockLimitedDemandedFriend,
                    mockLimitedSuggestedFriend);

            FriendUpdater.updateLimitedSuggestedFriend();

            verify(mockLimitedSuggestedFriend).getChildren().clear();
            verify(mockLimitedSuggestedFriend).getChildren().setAll(anyList());
        }
    }

    @Test
    public void testUpdateLimitedDemandedFriend() {
        try (MockedStatic<Initializer> mockedInitializer = Mockito.mockStatic(Initializer.class)) {
            mockedInitializer.when(Initializer::friendshipRequestReceiver).thenReturn(mockBeFriends.getBefriends());

            FriendUpdater instance = FriendUpdater.getInstance(
                    mockAcceptedFriend,
                    mockDemandedFriend,
                    mockSuggestedFriend,
                    mockLimitedDemandedFriend,
                    mockLimitedSuggestedFriend);

            FriendUpdater.updateLimitedDemandedFriend();

            verify(mockLimitedDemandedFriend).getChildren().clear();
            verify(mockLimitedDemandedFriend).getChildren().setAll(anyList());
        }
    }
}
