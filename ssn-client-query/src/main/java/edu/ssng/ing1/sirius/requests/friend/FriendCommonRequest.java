package edu.ssng.ing1.sirius.requests.friend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.ssng.ing1.BeFriend;
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

    public static Students selectFriends(BeFriend friend_relation)
            throws NullPointerException, IOException, InterruptedException {
        final String LoggingLabel = "S E L E C T - F R I E N D S";
        final Logger logger = LoggerFactory.getLogger(LoggingLabel);
        final String threadName = "select-friends";
        Deque<ClientRequest<Object, Students>> clientRequests = new ArrayDeque<>();
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());
        String requestOrder = "SELECT_FRIENDS";
        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedStudent = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(friend_relation);
        logger.trace("Student with its JSON face : {}", jsonifiedStudent);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedStudent);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final SelectFriendsClientRequest clientRequest_ = new SelectFriendsClientRequest(
                networkConfig,
                birthdate++, request, friend_relation, requestBytes);
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
