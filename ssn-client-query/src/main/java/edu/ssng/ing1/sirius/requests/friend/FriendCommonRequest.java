package edu.ssng.ing1.sirius.requests.friend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.ssng.ing1.sirius.business.dto.BeFriends;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.business.dto.Students;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.ConfigLoader;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

import java.util.*;

public class FriendCommonRequest {

    private final static String networkConfigFile = "network.yaml";

    public static BeFriends selectFriends(Student receiver)
            throws NullPointerException, IOException, InterruptedException {
        final String LoggingLabel = "S E L E C T - F R I E N D S";
        final Logger logger = LoggerFactory.getLogger(LoggingLabel);
        final String threadName = "select-friends";
        Deque<ClientRequest<Object, BeFriends>> clientRequests = new ArrayDeque<>();
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());
        String requestOrder = "SELECT_FRIENDS";
        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedStudent = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(receiver);
        logger.trace("Friends with its JSON face : {}", jsonifiedStudent);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedStudent);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final SelectFriendsClientRequest clientRequest_ = new SelectFriendsClientRequest(
                networkConfig,
                birthdate++, request, receiver, requestBytes);
        clientRequests.push(clientRequest_);

        while (!clientRequests.isEmpty()) {
            final ClientRequest<Object, BeFriends> joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", threadName);
            final BeFriends friends = (BeFriends) joinedClientRequest.getResult();
            return friends;
        }
        return null;
    }

    public static Map<String, String> deleteInvitation(Map<String, Integer> map)
            throws NullPointerException, IOException, InterruptedException {
        Deque<ClientRequest<Object, Map<String, String>>> clientRequests_ = new ArrayDeque<ClientRequest<Object, Map<String, String>>>();
        final String LoggingLabel = "D E L E T E - I N V I T A T I O N";
        final Logger logger = LoggerFactory.getLogger(LoggingLabel);
        final String networkConfigFile = "network.yaml";
        String requestOrder;

        requestOrder = "REJECT_INVITATION";

        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedStudent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedStudent);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final FriendDemandRequest clientRequest_ = new FriendDemandRequest(
                networkConfig,
                birthdate++, request, map, requestBytes);
        clientRequests_.push(clientRequest_);

        while (!clientRequests_.isEmpty()) {
            final ClientRequest<Object, Map<String, String>> joinedClientRequest = clientRequests_.pop();
            joinedClientRequest.join();
            final Map<String, String> result = (Map<String, String>) joinedClientRequest.getResult();
            return result;
        }
        return null;
    }

    public static Map<String, String> becomeFriend(Map<String, Integer> map)
            throws NullPointerException, IOException, InterruptedException {
        Deque<ClientRequest<Object, Map<String, String>>> clientRequests_ = new ArrayDeque<ClientRequest<Object, Map<String, String>>>();
        final String LoggingLabel = "B E C O M E - F R I E N D S";
        final Logger logger = LoggerFactory.getLogger(LoggingLabel);
        final String networkConfigFile = "network.yaml";
        String requestOrder;

        requestOrder = "BECOME_FRIENDS";

        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedStudent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedStudent);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final FriendDemandRequest clientRequest_ = new FriendDemandRequest(
                networkConfig,
                birthdate++, request, map, requestBytes);
        clientRequests_.push(clientRequest_);

        while (!clientRequests_.isEmpty()) {
            final ClientRequest<Object, Map<String, String>> joinedClientRequest = clientRequests_.pop();
            joinedClientRequest.join();
            final Map<String, String> result = (Map<String, String>) joinedClientRequest.getResult();
            return result;
        }
        return null;
    }

    public static Students selectSuggestedFriends(Student user) throws IOException, InterruptedException {
        final String LoggingLabel = "S E L E C T - S U G G E S T E D - F R I E N D S";
        final Logger logger = LoggerFactory.getLogger(LoggingLabel);
        final String threadName = "select-suggested-friends";
        Deque<ClientRequest<Object, Students>> clientRequests = new ArrayDeque<>();
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());
        String requestOrder = "SELECT_SUGGESTED_FRIENDS";
        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedStudent = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(user);
        logger.trace("Friends with its JSON face : {}", jsonifiedStudent);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedStudent);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final SelectStudents clientRequest_ = new SelectStudents(
                networkConfig,
                birthdate++, request, user, requestBytes);
        clientRequests.push(clientRequest_);

        while (!clientRequests.isEmpty()) {
            final ClientRequest<Object, Students> joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", threadName);
            final Students students = (Students) joinedClientRequest.getResult();
            return students;
        }
        return null;
    }
}
