package edu.ssng.ing1.sirius.client.requests.self;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;
import java.io.IOException;

public class SelectSelfRequest extends ClientRequest<Object, Student> {

    public SelectSelfRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Student readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Student student = mapper.readValue(body, Student.class);
        return student;
    }
}