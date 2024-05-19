package edu.ssng.ing1.sirius.requests.Disconnection;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.ssng.commons.LoggingUtils;
import edu.ssng.ing1.sirius.business.dto.Cities;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.ConfigLoader;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;
import edu.ssng.ing1.sirius.requests.authentification.SelectAllCitiesClientRequest;
import edu.ssng.ing1.sirius.requests.authentification.SignInClientRequest;

public class Disconnection {
    private final static String networkConfigFile = "network.yaml";


    public static String disconnection(String email) throws NullPointerException, IOException, InterruptedException {
        final  String LoggingLabel = "D i s c o n e c t i o n - S t u d e n t";
        final  Logger logger = LoggerFactory.getLogger(LoggingLabel);
        final String threadName = "Disconnection Student";
    
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());
        String requestOrder = "DISCONNECTION_STUDENT";
        int birthdate = 0;
        Student student= new Student();
        student.setEmail(email);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        String body=objectMapper.writeValueAsString(student);

        final Request request = new Request();
        request.setRequestContent(body);
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);

        final SignInClientRequest clientRequest = new SignInClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
                logger.debug("Thread {} complete.", threadName);
        return clientRequest.getResult();
           
            
        

    }
}
