package edu.ssng.ing1.sirius.requests.messages;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.ssng.ing1.sirius.business.dto.Messages;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.ConfigLoader;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;

public class CommonsMessageRequest {
    private final static String networkConfigFile = "network.yaml";

    public static Messages selectMessages(int student_id)
            throws NullPointerException, IOException, InterruptedException {
        final String LoggingLabel = "F E T C H - S T U D E N T - M E S S A G E S";
        final Logger logger = LoggerFactory.getLogger(LoggingLabel);
        final String threadName = "fetch-student-messages";
        Deque<ClientRequest<Object, Messages>> clientRequests = new ArrayDeque<>();
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());
        String requestOrder = "FETCH_STUDENT_MESSAGES";
        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedUser = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(student_id);
        logger.trace("User with its JSON face : {}", jsonifiedUser);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedUser);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final SelectMessagesClientRequest clientRequest_ = new SelectMessagesClientRequest(
                networkConfig,
                birthdate++, request, student_id, requestBytes);
        clientRequests.push(clientRequest_);

        while (!clientRequests.isEmpty()) {
            final ClientRequest<Object, Messages> joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", threadName);
            final Messages message = (Messages) joinedClientRequest.getResult();
            return message;
        }
        return null;
    }

}
