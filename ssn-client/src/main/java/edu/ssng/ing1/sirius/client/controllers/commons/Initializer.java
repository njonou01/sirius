package edu.ssng.ing1.sirius.client.controllers.commons;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import edu.ssng.ing1.sirius.business.dto.BeFriend;
import edu.ssng.ing1.sirius.business.dto.BeFriends;
import edu.ssng.ing1.sirius.business.dto.Message;
import edu.ssng.ing1.sirius.business.dto.Messages;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.business.dto.Students;
import edu.ssng.ing1.sirius.client.router.Router;
import edu.ssng.ing1.sirius.requests.friend.FriendCommonRequest;
import edu.ssng.ing1.sirius.requests.messages.CommonsMessageRequest;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class Initializer {
        static BeFriends friends;
        static Messages messages;
        static Students suggestions;

        public static synchronized BeFriends getinvitations() {
                return friends;
        }

        public static synchronized Students getSuggestions() {
                return suggestions;
        }

        public static synchronized BorderPane initInvitationPage() {
                FXMLLoader fxml = Router.getInstance().getParentNode("friend-page");
                System.out.println(fxml);
                try {
                        return (BorderPane) fxml.load();
                } catch (IOException e) {
                        return null;
                }
        }

        public static synchronized BorderPane initMessagingPage() {
                FXMLLoader fxml = Router.getInstance().getParentNode("messaging");
                try {
                        BorderPane pane = (BorderPane) fxml.load();
                        fxml.getController();

                        return pane;
                } catch (IOException e) {
                        return null;
                }
        }

        public static synchronized void invitationsFetcher() {
                try {
                        int student_id = UserInfo.getUser().getId_student();
                        friends = FriendCommonRequest.selectFriends(new Student(student_id));
                } catch (NullPointerException | IOException | InterruptedException e) {
                        friends = new BeFriends();
                }
        }

        public static synchronized void suggestionsFetcher() {
                try {
                        int student_id = UserInfo.getUser().getId_student();
                        suggestions = FriendCommonRequest.selectSuggestedFriends(new Student(student_id));
                } catch (NullPointerException | IOException | InterruptedException e) {
                        suggestions = new Students();
                        e.printStackTrace();
                }
        }

        public static synchronized void messagesFetcher() {
                try {
                        int student_id = UserInfo.getUser().getId_student();
                        messages = CommonsMessageRequest.selectMessages(student_id);
                } catch (NullPointerException | IOException | InterruptedException e) {
                        messages = new Messages();
                }
        }

        public static Set<BeFriend> getFriends() {
                Set<BeFriend> acceptedfriend = friends.getBefriends()
                                .stream()
                                .filter(f -> f.getStatus().equalsIgnoreCase("accepted"))
                                .collect(Collectors.toSet());
                return acceptedfriend;
        }

        public static synchronized Set<BeFriend> friendshipRequestReceiver() {
                Set<BeFriend> friendshipRequest = friends.getBefriends()
                                .stream()
                                .filter(f -> f.getStatus().equalsIgnoreCase("no reponse"))
                                .collect(Collectors.toSet());
                return friendshipRequest;
        }

        public static synchronized Set<Message> getAllMessages() {
                // Message message1 = new Message(1, 952, 44,
                // "Salut gars ! J'ai rencontré un problème avec la configuration du serveur
                // pour Sirius.",
                // null,
                // Timestamp.valueOf("2024-05-01 10:00:00"));
                // Message message2 = new Message(2, 44, 952, "Salut ! Quel est le problème
                // exactement ?", null,
                // Timestamp.valueOf("2024-05-01 10:05:00"));
                // Message message3 = new Message(3, 952, 44,
                // "Je ne parviens pas à faire fonctionner la connexion à la base de données.",
                // null,
                // Timestamp.valueOf("2024-05-01 10:10:00"));
                // Message message4 = new Message(4, 44, 952,
                // "As-tu vérifié les paramètres de configuration de la base de données dans le
                // fichier de configuration ?",
                // null, Timestamp.valueOf("2024-05-01 10:15:00"));
                // Message message5 = new Message(5, 952, 44,
                // "Oui, mais je pense que j'ai manqué quelque chose. Peux-tu jeter un coup
                // d'œil ?",
                // null,
                // Timestamp.valueOf("2024-05-01 10:20:00"));
                // Message message6 = new Message(6, 44, 952, "Bien sûr. Donne-moi quelques
                // minutes.", null,
                // Timestamp.valueOf("2024-05-01 10:25:00"));
                // Message message7 = new Message(7, 952, 44, "D'accord, merci !", null,
                // Timestamp.valueOf("2024-05-01 10:30:00"));
                // Message message8 = new Message(8, 44, 952,
                // "Je pense avoir trouvé le problème. Il y avait une erreur de syntaxe dans le
                // fichier de configuration.",
                // null, Timestamp.valueOf("2024-05-01 10:35:00"));
                // Message message9 = new Message(9, 952, 44,
                // "Ah, d'accord. Je vais corriger ça. Merci beaucoup !", null,
                // Timestamp.valueOf("2024-05-01 10:40:00"));
                // Message message10 = new Message(10, 44, 952,
                // "Pas de soucis. Tiens-moi au courant si tu as besoin d'aide pour autre
                // chose.",
                // null,
                // Timestamp.valueOf("2024-05-01 10:45:00"));
                // Message message11 = new Message(11, 952, 44, "Merci !", null,
                // Timestamp.valueOf("2024-05-01 10:50:00"));
                // Message message12 = new Message(12, 44, 952, "Salut ! Comment ça avance de
                // ton côté ?", null,
                // Timestamp.valueOf("2024-05-01 10:55:00"));
                // Message message13 = new Message(13, 952, 44,
                // "Tout roule ! J'ai résolu le problème et maintenant je suis en train de
                // travailler sur l'interface utilisateur.",
                // null, Timestamp.valueOf("2024-05-01 11:00:00"));
                // Message message14 = new Message(14, 44, 952, "Génial ! Tu as besoin d'un coup
                // de main ?", null,
                // Timestamp.valueOf("2024-05-01 11:05:00"));
                // Message message15 = new Message(15, 952, 44,
                // "Non, je devrais m'en sortir. Mais merci quand même !", null,
                // Timestamp.valueOf("2024-05-01 11:10:00"));
                // Message message16 = new Message(16, 44, 952, "OK, n'hésite pas si tu changes
                // d'avis.", null,
                // Timestamp.valueOf("2024-05-01 11:15:00"));
                // Message message17 = new Message(17, 952, 44,
                // "C'est noté. Bon, je vais me replonger dans le code.", null,
                // Timestamp.valueOf("2024-05-01 11:20:00"));
                // Message message18 = new Message(18, 44, 952, "Bonne chance !", null,
                // Timestamp.valueOf("2024-05-01 11:25:00"));
                // Message message19 = new Message(19, 952, 44, "Merci !", null,
                // Timestamp.valueOf("2024-05-01 11:30:00"));
                // Message message20 = new Message(20, 44, 952, "À plus tard !", null,
                // Timestamp.valueOf("2024-05-01 11:35:00"));

                // Set<Message> listOfMessages = Set.of(message1,
                // message2,
                // message3,
                // message4,
                // message5,
                // message6,
                // message7,
                // message8,
                // message9,
                // message10,
                // message11,
                // message12,
                // message13,
                // message14,
                // message15,
                // message16,
                // message17,
                // message18,
                // message19, message20);
                return messages.getMessages();
        }

        public static synchronized List<Message> getMessagesOfStudent(int id_student) {
                return getAllMessages().stream()
                                .filter(msg -> msg.getSenderId() == id_student || msg.getReceiverId() == id_student)
                                .sorted((msg1, msg2) -> msg1.getSentAt().compareTo(msg2.getSentAt()))
                                .collect(Collectors.toList());
        }

}
