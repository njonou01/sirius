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
import edu.ssng.ing1.sirius.commons.SomeInfo;

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
import java.util.HashMap;
import java.util.UUID;

public class AcceptActivityRequest {

    private final static String LoggingLabel = "I n s e r t e r - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String studentsToBeInserted = "students-to-be-inserted.yaml";
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "inserter-client";

    private static final Deque<SelectStudentClientRequest> clientRequests = new ArrayDeque<SelectStudentClientRequest>();



    public static void acceptActivity(Integer idStudent ,String studentEmail, Integer idActivityCreator, String email) throws IOException, InterruptedException, SQLException {

        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        SomeInfo someInfo = new SomeInfo();
        someInfo.setInfo(email);
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("id_student", idStudent+"");
        hashMap.put("idActivityCreator", idActivityCreator+"");
        hashMap.put("studentEmail", studentEmail);
        hashMap.put("email", email);


        someInfo.setMapInfo(hashMap);
        String body=objectMapper.writeValueAsString(someInfo);
        request.setRequestContent(body);
        request.setRequestOrder("INSERT_PARTICIPATION");
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);

        
        final SelectStudentClientRequest clientRequest = new SelectStudentClientRequest(
                                                                    networkConfig,
                                                                    birthdate++, request, null, requestBytes);
                                                                  
        clientRequests.push(clientRequest);

        
            final SelectStudentClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            final Students students = (Students) joinedClientRequest.getResult();

           
    }
    public static void denyActivity(String studentEmail, String email) throws IOException, InterruptedException, SQLException {

        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        SomeInfo someInfo = new SomeInfo();
        someInfo.setInfo(email);
        HashMap<String,String> hashMap=new HashMap<>();
        
        hashMap.put("studentEmail", studentEmail);
        hashMap.put("email", email);


        someInfo.setMapInfo(hashMap);
        String body=objectMapper.writeValueAsString(someInfo);
        request.setRequestContent(body);
        request.setRequestOrder("DENY_INVITATION");
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);

        
        final SelectStudentClientRequest clientRequest = new SelectStudentClientRequest(
                                                                    networkConfig,
                                                                    birthdate++, request, null, requestBytes);
                                                                  
        clientRequests.push(clientRequest);

        
            final SelectStudentClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            final Students students = (Students) joinedClientRequest.getResult();

           
    }
    
}
