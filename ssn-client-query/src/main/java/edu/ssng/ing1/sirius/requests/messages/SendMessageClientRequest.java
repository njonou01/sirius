package edu.ssng.ing1.sirius.requests.messages;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.business.dto.Message;
import edu.ssng.ing1.sirius.business.dto.Messages;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;

public class SendMessageClientRequest extends ClientRequest<Object, Object> {

    public SendMessageClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Object readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final Message response = mapper.readValue(body, Message.class);
            return response;
        } catch (IOException e) {
            final Map<String, Object> response = mapper.readValue(body, new TypeReference<Map<String, String>>() {
            });
            return response;
        }

    }
}