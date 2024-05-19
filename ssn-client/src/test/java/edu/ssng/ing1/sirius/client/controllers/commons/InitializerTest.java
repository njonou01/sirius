// package edu.ssng.ing1.sirius.client.controllers.commons;

// import edu.ssng.ing1.sirius.business.dto.BeFriend;
// import edu.ssng.ing1.sirius.business.dto.BeFriends;
// import edu.ssng.ing1.sirius.business.dto.Message;
// import edu.ssng.ing1.sirius.business.dto.Messages;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.sql.Timestamp;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Set;
// import java.util.stream.Collectors;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.when;

// @ExtendWith(MockitoExtension.class)
// public class InitializerTest {

//     @InjectMocks
//     private Initializer initializer;

//     @Mock
//     private BeFriends friends;

//     @Mock
//     private Messages messages;

//     @BeforeEach
//     void setUp() {
//         Set<BeFriend> friendSet = new HashSet<>();
//         for (int i = 1; i <= 10; i++) {
//             BeFriend friend = new BeFriend();
//             if (i % 2 == 0) {
//                 friend.setStatus("accepted");
//             } else {
//                 friend.setStatus("no response");
//             }
//             friendSet.add(friend);
//         }

//         when(friends.getBefriends()).thenReturn(friendSet);

//         Set<Message> messageSet = new HashSet<>();
//         for (int i = 1; i <= 10; i++) {
//             Message message = new Message(i, i, i + 1, "Message " + i, null, Timestamp.valueOf("2024-05-01 10:00:00"));
//             messageSet.add(message);
//         }

//         when(messages.getMessages()).thenReturn(messageSet);

//         Initializer.friends = friends;
//         Initializer.messages = messages;
//     }

//     @Test
//     void testGetFriends() {
//         Set<BeFriend> acceptedFriends = Initializer.getFriends();
//         assertEquals(5, acceptedFriends.size());
//         acceptedFriends.forEach(friend -> assertEquals("accepted", friend.getStatus()));
//     }

//     @Test
//     void testFriendshipRequestReceiver() {
//         Set<BeFriend> friendshipRequests = Initializer.friendshipRequestReceiver();
//         assertEquals(5, friendshipRequests.size());
//         friendshipRequests.forEach(friend -> assertEquals("no response", friend.getStatus()));
//     }

//     @Test
//     void testGetAllMessages() {
//         Set<Message> result = Initializer.getAllMessages();
//         assertEquals(10, result.size());
//     }

//     @Test
//     void testGetMessagesOfStudent() {
//         List<Message> result = Initializer.getMessagesOfStudent(1);
//         List<Message> expected = messages.getMessages().stream()
//                 .filter(msg -> msg.getSenderId() == 1 || msg.getReceiverId() == 1)
//                 .sorted((msg1, msg2) -> msg1.getSentAt().compareTo(msg2.getSentAt()))
//                 .collect(Collectors.toList());

//         assertEquals(expected, result);
//     }
// }
