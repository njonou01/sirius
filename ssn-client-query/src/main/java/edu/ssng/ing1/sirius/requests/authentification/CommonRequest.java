package edu.ssng.ing1.sirius.requests.authentification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.ssng.commons.LoggingUtils;
import edu.ssng.ing1.sirius.business.dto.Cities;
import edu.ssng.ing1.sirius.business.dto.Universities;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.ConfigLoader;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;

import java.util.*;

public class CommonRequest {

    private final static String networkConfigFile = "network.yaml";    

    public static Cities selectCities() throws NullPointerException, IOException, InterruptedException {
        final  String LoggingLabel = "S E L E C T - C I T I E S";
        final  Logger logger = LoggerFactory.getLogger(LoggingLabel);
        final String threadName = "select-all-cities";
    
        Deque<ClientRequest<Object, Cities>> clientRequests = new ArrayDeque<>();
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());
        String requestOrder = "SELECT_ALL_CITIES";
        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllCitiesClientRequest clientRequest = new SelectAllCitiesClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest<Object,Cities> joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", threadName);
            final Cities cities = (Cities) joinedClientRequest.getResult();
            return cities;
        }
        return null;
    }

    public static Universities selectUniversities() throws NullPointerException, IOException, InterruptedException {
        final  String LoggingLabel = "S E L E C T - U N I V E R S I T I E S";
        final  Logger logger = LoggerFactory.getLogger(LoggingLabel);
        final String threadName = "select-all-universities";
        Deque<ClientRequest<Object, Universities>> clientRequests = new ArrayDeque<>();
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());
        String requestOrder = "SELECT_ALL_UNIVERSITIES";
        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllUniversitiesClientRequest clientRequest = new SelectAllUniversitiesClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest<Object, Universities> joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", threadName);
            final Universities universities = (Universities) joinedClientRequest.getResult();
            return universities;
        }
        return null;
    }
}
