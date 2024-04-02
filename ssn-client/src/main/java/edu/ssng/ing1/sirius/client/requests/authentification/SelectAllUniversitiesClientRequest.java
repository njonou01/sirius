package edu.ssng.ing1.sirius.client.requests.authentification;


import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.business.dto.Universities;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;

import java.io.IOException;

public class SelectAllUniversitiesClientRequest extends ClientRequest<Object, Universities> {

    public SelectAllUniversitiesClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Universities readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Universities universities = mapper.readValue(body, Universities.class);
        return universities;
    }
}