package edu.ssng.ing1.sirius.requests.authentification;


import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.business.dto.Cities;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;

import java.io.IOException;

public class SelectAllCitiesClientRequest extends ClientRequest<Object, Cities> {

    public SelectAllCitiesClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Cities readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Cities cities = mapper.readValue(body, Cities.class);
        return cities;
    }
}