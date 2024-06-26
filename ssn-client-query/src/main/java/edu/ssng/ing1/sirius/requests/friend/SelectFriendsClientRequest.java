package edu.ssng.ing1.sirius.requests.friend;


import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.business.dto.BeFriends;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;

import java.io.IOException;

public class SelectFriendsClientRequest extends ClientRequest<Object, BeFriends> {

    public SelectFriendsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public BeFriends readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final BeFriends befriends = mapper.readValue(body,BeFriends.class);
        return befriends;
    }
}