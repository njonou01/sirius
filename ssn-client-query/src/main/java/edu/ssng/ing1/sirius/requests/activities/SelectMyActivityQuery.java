package edu.ssng.ing1.sirius.requests.activities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.ssng.commons.LoggingUtils;
import edu.ssng.ing1.sirius.business.dto.Activite;
import edu.ssng.ing1.sirius.business.dto.Activites;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.business.dto.Students;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.ConfigLoader;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class SelectMyActivityQuery {

    private final static String LoggingLabel = "I n s e r t e r - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String studentsToBeInserted = "students-to-be-inserted.yaml";
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "inserter-client";

    private static final Deque<SelectActivitesClientRequest> clientRequests = new ArrayDeque<SelectActivitesClientRequest>();


public static void main(String[] args) throws IOException, InterruptedException, SQLException {
    Student student= new Student();
    student.setId_student(3);
    SelectActivite(student);
}



    public static Activites SelectActivite(Student student) throws IOException, InterruptedException, SQLException {

        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        String body=objectMapper.writeValueAsString(student);
        request.setRequestContent(body);
        request.setRequestOrder("SELECT_MY_ACTIVITY");
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);

        
        final SelectActivitesClientRequest clientRequest = new SelectActivitesClientRequest(
                                                                    networkConfig,
                                                                    birthdate++, request, null, requestBytes);
                                                                  
        clientRequests.push(clientRequest);

        
            final SelectActivitesClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            final Activites students = (Activites) joinedClientRequest.getResult();
           
        return students;
    }
}
