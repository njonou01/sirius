package edu.ssng.ing1.sirius.requests.friend;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;

class FriendDemandRequest extends ClientRequest<Object, Map<String , String>> {

        public FriendDemandRequest(
                NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
                throws IOException {
            super(networkConfig, myBirthDate, request, info, bytes);
        }

        @Override
        public Map<String,String> readResult(String body) throws IOException {
            final ObjectMapper mapper = new ObjectMapper();
            final Map<String , String> response = mapper.readValue(body, new TypeReference<Map<String, String>>() {});
            return response;
        }
    }