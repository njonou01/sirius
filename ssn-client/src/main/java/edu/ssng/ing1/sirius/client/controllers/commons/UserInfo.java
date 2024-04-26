package edu.ssng.ing1.sirius.client.controllers.commons;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;
import java.util.prefs.Preferences;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.MainClient;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.ConfigLoader;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;

public class UserInfo {
    private static UserInfo instance;
    private static Student user;
    private static String ssnemailpref = "SSN_USER_EMAIL";
    private static Preferences prefs = Preferences.userRoot()
            .node(MainClient.class.getName());
    private final static Logger logger = LoggerFactory.getLogger("U S E R - I N F O");

    private UserInfo() {

    }

    public static UserInfo getInstance() {
        if (instance == null) {
            instance = new UserInfo();
            String sysEmail = getPresentEmailOnSystem();
            if (sysEmail != null) {
                setUser(sysEmail);
                String fullname = user.getFamilyname() + " " + user.getFirstname();
                logger.debug("Connectéé en tant que {} ", fullname);
            }
        }
        return instance;
    }

    public static Student getUser() {
        if (isUserExistInComputer())
            return null;
        return user;
    }

    public static void registerUser(String email) {
        prefs.put(ssnemailpref, email);
        setUser(email);
    }

    public static void removeUser() {
        prefs.remove(ssnemailpref);
        user = null;
    }

    private static void setUser(String email) {
        try {
            user = getSelfInfo(email);
        } catch (NullPointerException | IOException | InterruptedException e) {
            user = null;
        }
    }

    private static Boolean isUserExistInComputer() {
        return prefs.get(ssnemailpref, null) == null;
    }

    private static String getPresentEmailOnSystem() {
        return prefs.get(ssnemailpref, null);
    }

    public static Student getSelfInfo(String email) throws NullPointerException, IOException, InterruptedException {
        Deque<ClientRequest<Object, Student>> clientRequests_ = new ArrayDeque<ClientRequest<Object, Student>>();
        final String LoggingLabel = "S T U D E N T - I N FO ";
        final Logger logger = LoggerFactory.getLogger(LoggingLabel);
        final String networkConfigFile = "network.yaml";
        String requestOrder;

        requestOrder = "STUDENT_INFO";

        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedStudent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(email);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedStudent);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final SelectSelfInfoRequest clientRequest_ = new SelectSelfInfoRequest(
                networkConfig,
                birthdate++, request, email, requestBytes);
        clientRequests_.push(clientRequest_);

        while (!clientRequests_.isEmpty()) {
            final ClientRequest<Object, Student> joinedClientRequest = clientRequests_.pop();
            joinedClientRequest.join();
            final Student student_return = (Student) joinedClientRequest.getResult();
            return student_return;
        }
        return null;
    }

    private static class SelectSelfInfoRequest extends ClientRequest<Object, Student> {

        public SelectSelfInfoRequest(
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

}
