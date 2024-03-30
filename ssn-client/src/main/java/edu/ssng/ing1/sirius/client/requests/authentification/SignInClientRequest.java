package edu.ssng.ing1.sirius.client.requests.authentification;

import java.io.IOException;


import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;

public class SignInClientRequest extends ClientRequest<Student, String> {

    public SignInClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Student info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final String result = body;
        return result;
    }
}