package edu.ssng.ing1.sirius.requests.activities;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.business.dto.Activites;
import edu.ssng.ing1.sirius.business.dto.Students;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;

import java.io.IOException;

public class SelectStudentClientRequest extends ClientRequest<Object, Students> {

    public SelectStudentClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Students readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Students students = mapper.readValue(body, Students.class);
        return students;
    }
}