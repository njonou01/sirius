package edu.ssng.ing1.sirius.requests.activities;

import com.fasterxml.jackson.databind.ObjectMapper;




import edu.ssng.ing1.sirius.business.dto.Activite;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;

import java.io.IOException;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;

public class InsertActivityClientRequest extends ClientRequest<Activite, String> {

    public InsertActivityClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Activite info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    public void setResult(String Result){
        this.result=Result;
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> studentIdMap = mapper.readValue(body, Map.class);
        final String result  = studentIdMap.get("student_id").toString();
        return result;
    }
}
