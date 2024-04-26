package edu.ssng.ing1.sirius.requests.authentification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.ConfigLoader;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import java.util.*;

public class AuthRequest {

    private final static String networkConfigFile = "network.yaml";
    private static String requestOrder;
    private static final Deque<ClientRequest<Student, String>> clientRequests = new ArrayDeque<ClientRequest<Student, String>>();

    public static void signUpAs(Student student) throws NullPointerException, IOException, InterruptedException {
        final String LoggingLabel = "C R E A T E - U S E R - C L I E N T";
        final Logger logger = LoggerFactory.getLogger(LoggingLabel);
        final String threadName = "create-user";

        requestOrder = "INSERT_STUDENT";
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.trace("Students loaded : {}", student.toString());
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedStudent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);
        logger.trace("Student with its JSON face : {}", jsonifiedStudent);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedStudent);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final SignInClientRequest clientRequest_ = new SignInClientRequest(
                networkConfig,
                birthdate++, request, student, requestBytes);
        clientRequests.push(clientRequest_);

        while (!clientRequests.isEmpty()) {
            final ClientRequest<Student, String> clientRequest = clientRequests.pop();
            clientRequest.join();
            final Student student_response = (Student) clientRequest.getInfo();
            logger.debug("Thread {} complete : {}  --> {}",
                    threadName, student_response.toString(),
                    clientRequest.getResult());
        }
    }

    public static Boolean signInAs(Student student) throws NullPointerException, IOException, InterruptedException {
        final String LoggingLabel = "S I G N - I N - C L I E N T";
        final Logger logger = LoggerFactory.getLogger(LoggingLabel);
        final String threadName = "signin-client";

        requestOrder = "SIGN_IN_AS";

        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedStudent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);
        logger.trace("Student with its JSON face : {}", jsonifiedStudent);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedStudent);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final SignInClientRequest clientRequest_ = new SignInClientRequest(
                networkConfig,
                birthdate++, request, student, requestBytes);
        clientRequests.push(clientRequest_);

        while (!clientRequests.isEmpty()) {
            final ClientRequest<Student, String> joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", threadName);
            String signResponse = joinedClientRequest.getResult();
            return signResponse.equalsIgnoreCase("success");
        }
        return false;
    }

    public static Boolean isUser(Student student) throws NullPointerException, IOException, InterruptedException {
        final String LoggingLabel = "D O E S - U S E R - E X S I S T - C L I E N T";
        final Logger logger = LoggerFactory.getLogger(LoggingLabel);
        final String threadName = "doest-user-exist-client";

        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        requestOrder = "DOES_STUDENT_EXIST";

        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedStudent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);
        logger.trace("Student with its JSON face : {}", jsonifiedStudent);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedStudent);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final IsUserClientRequest clientRequest_ = new IsUserClientRequest(
                networkConfig,
                birthdate++, request, student, requestBytes);
        clientRequests.push(clientRequest_);

        while (!clientRequests.isEmpty()) {
            final ClientRequest<Student, String> clientRequest = clientRequests.pop();
            clientRequest.join();
            final Student student_response = (Student) clientRequest.getInfo();
            logger.debug("Thread {} complete : {}  --> {}",
                    threadName, student_response.toString(),
                    clientRequest.getResult());
            return clientRequest.getResult().equals("success");
        }
        return null;
    }
}