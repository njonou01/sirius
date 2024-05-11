package edu.ssng.ing1.sirius.requests.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ssng.ing1.sirius.business.dto.Messages;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;

import java.io.IOException;

public class SelectMessagesClientRequest extends ClientRequest<Object, Messages> {

    public SelectMessagesClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Messages readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Messages messages = mapper.readValue(body, Messages.class);
        return messages;
    }
}