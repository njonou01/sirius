package edu.ssng.ing1.sirius.business.server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.similarity.JaccardDistance;

import edu.ssng.ing1.sirius.business.dto.BeFriend;
import edu.ssng.ing1.sirius.business.dto.BeFriends;
import edu.ssng.ing1.sirius.business.dto.Activite;
import edu.ssng.ing1.sirius.business.dto.Activites;
import edu.ssng.ing1.sirius.business.dto.Cities;
import edu.ssng.ing1.sirius.business.dto.City;
import edu.ssng.ing1.sirius.business.dto.Message;
import edu.ssng.ing1.sirius.business.dto.Messages;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.business.dto.Students;
import edu.ssng.ing1.sirius.business.dto.Universities;
import edu.ssng.ing1.sirius.business.dto.University;
import edu.ssng.ing1.sirius.commons.Request;
import edu.ssng.ing1.sirius.commons.Response;
import org.mindrot.jbcrypt.BCrypt;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.UUID;
import java.util.stream.Collectors;

public class XMartCityService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    public static XMartCityService inst = null;

    public static final XMartCityService getInstance() {
        if (inst == null) {
            inst = new XMartCityService();
        }
        return inst;
    }

    private XMartCityService() {

    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException {

        try {
            Queries query = Queries.valueOf(request.getRequestOrder());

            PreparedStatement preparedStatement = connection.prepareStatement(query.getQuery());

            switch (query) {
                case SELECT_ALL_ACTIVITY:
                    return SelectAllActivite(preparedStatement);
                case SELECT_MY_ACTIVITY:
                    return SelectMyActivite(request, preparedStatement);
                case SELECT_ALL_STUDENTS:
                    return selectStudentsResponse(preparedStatement, request);
                case INSERT_ACTIVITY:
                    return InsertActivite(preparedStatement, request);
                case SELECT_ALL_CITIES:
                    return selectCitiesResponse(preparedStatement, request);
                case SELECT_FRIEND_REQUEST_WITHOUT_ANSWER:
                    return selectStudentsResponse(preparedStatement, request);
                case SELECT_FRIENDS:
                    return selectFriendResponse(preparedStatement, request);
                case SELECT_ALL_UNIVERSITIES:
                    return selectUniversitiesResponse(preparedStatement, request);
                case INSERT_STUDENT:
                    return insertStudentResponse(preparedStatement, request);
                case DOES_STUDENT_EXIST:
                    return doesStudentExistResponse(preparedStatement, request);
                case SELECT_SUGGESTED_FRIENDS:
                    return selectSuggestedFriendsResponse(preparedStatement, request);
                case SIGN_IN_AS:
                    return signinAsResponse(preparedStatement, request);
                case STUDENT_INFO:
                    return selectSelfInfoResponse(preparedStatement, request);
                case GET_DATA_CONNEXION:
                    return getConnexionData(preparedStatement, request);
                case REJECT_INVITATION:
                    return deleteInvitationResponse(preparedStatement, request);
                case BECOME_FRIENDS:
                    return becomeFriendsResponse(preparedStatement, request);
                case FETCH_STUDENT_MESSAGES:
                    return fetchStudentMessages(preparedStatement, request);
                case SEND_AND_SAVE_MESSAGE:
                    return sendAndSaveMessage(preparedStatement, request);
                case ASK_FRIENDSHIP:
                    return addFriendShipResponse(preparedStatement, request);
                default:
                    break;
            }
            preparedStatement.close();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + " --> " + e.getClass());
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " --> " + e.getClass());
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            System.out.println(e.getMessage() + " --> " + e.getClass());
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage() + " --> " + e.getClass());
        } catch (IOException e) {
            System.out.println(e.getMessage() + " --> " + e.getClass());
        }

        return new Response("error", "Requete impossible");
    }

    private Response addFriendShipResponse(PreparedStatement preparedStatement, Request request)
            throws NoSuchFieldException,
            IllegalAccessException, SQLException, JsonParseException, JsonMappingException, IOException {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            Map<String, Integer> map = mapper.readValue(request.getRequestBody(),
                    new TypeReference<Map<String, Integer>>() {
                    });

            preparedStatement.setInt(1, map.get("sender"));
            preparedStatement.setInt(2, map.get("receiver"));
            preparedStatement.executeUpdate();
            String bodyResponse = String.format("{\"response\": \"%s\"}", "success");
            return new Response(request.getRequestId(), bodyResponse);
        } catch (Exception e) {
            String bodyResponse = String.format("{\"response\": \"%s\"}", "error");
            return new Response(request.getRequestId(), bodyResponse);
        }

    }

    public static String generateUniqueFileName() {
        java.util.Date now = new java.util.Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(now);
        String uniqueID = UUID.randomUUID().toString().replaceAll("-", "");

        String extension = "png";

        String newFileName = "image_" + timestamp + "_" + uniqueID + "." + extension;

        return newFileName;
    }

    private Response sendAndSaveMessage(PreparedStatement preparedStatement, Request request) throws JsonParseException,
            JsonMappingException, IOException, NoSuchFieldException, IllegalAccessException, SQLException {
        final ObjectMapper mapper = new ObjectMapper();
        final Message message = mapper.readValue(request.getRequestBody(), Message.class);
        message.build(preparedStatement);
        String fileName = generateUniqueFileName();
        Boolean isSave = writeByteArrayToPNG(message.getMedia(), fileName);
        preparedStatement.setString(4, isSave ? fileName : null);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            final Message newMessage = new Message().build(resultSet);
            newMessage.setMedia(message.getMedia());
            resultSet.close();
            // Todo: send message to the other user
            return new Response(request.getRequestId(), mapper.writeValueAsString(newMessage));
        }
        return new Response(request.getRequestId(), "{\"error\": \"error\"}");
    }

    private Response fetchStudentMessages(PreparedStatement preparedStatement, Request request)
            throws NoSuchFieldException, IllegalAccessException {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            int student_id = mapper.readValue(request.getRequestBody(), Integer.class);
            preparedStatement.setInt(1, student_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Messages messages = new Messages();
            while (resultSet.next()) {
                Message message = new Message().build(resultSet);
                System.out.println(resultSet.getString("media"));
                String mediaEncoded = resultSet.getString("media");
                message.setMedia(
                        mediaEncoded != null ? getImageEncoded("media/conversation/images/" + mediaEncoded) : null);
                messages.add(message);
            }
            resultSet.close();
            String bodyResponse = mapper.writeValueAsString(messages);
            return new Response(request.getRequestId(), bodyResponse);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Response selectSuggestedFriendsResponse(PreparedStatement preparedStatement, Request request)
            throws SQLException, NoSuchFieldException, IllegalAccessException, IOException {
        Students students = new Students();
        final ObjectMapper mapper = new ObjectMapper();
        ObjectMapper objectMapper = new ObjectMapper();
        Student user = objectMapper.readValue(request.getRequestBody(), Student.class);
        preparedStatement.setInt(1, user.getId_student());
        String bodyResponse = "";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ResultSet listOfStudents = preparedStatement.executeQuery();
        while (listOfStudents.next()) {
            Student student = new Student().build(listOfStudents);
            student.setProfileImage(listOfStudents.getString("profile_image"));
            InputStream inputStream = classLoader.getResourceAsStream("media/images/" + student.getProfileImage());
            student.setProfileImageStream(convertInputStreamToBytes(inputStream));
            students.add(student);
        }
        listOfStudents.close();
        Students suggestedStudents = new Students();
        suggestedStudents.setStudents(students.getStudents()
                .stream()
                .limit(25)
                .collect(Collectors.toSet()));

        bodyResponse = mapper.writeValueAsString(suggestedStudents);
        return new Response(request.getRequestId(), bodyResponse);
    }

    private byte[] getImageEncoded(String path) throws IOException {
        if (path != null && !path.isEmpty()) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(path);
            return convertInputStreamToBytes(inputStream);
        }
        return null;
    }

    public static double compareStudents(Student student1, Student student2) {
        JaccardDistance jaccardDistance = new JaccardDistance();
        double distUniversity = jaccardDistance.apply(student1.getUniversity(), student2.getUniversity());
        new Student().getAdress();
        double distAdress = jaccardDistance.apply(student1.getAdress(), student2.getAdress());
        new Student().getFormation_description();
        double distFormationDescription = jaccardDistance.apply(student1.getFormation_description(),
                student2.getFormation_description());
        new Student().getGender();
        double distGender = student1.getGender().equals(student2.getGender()) ? 1 : 0;

        return Math.sqrt(Math.pow(distUniversity, 2) + Math.pow(distAdress, 2) + Math.pow(distFormationDescription, 2)
                + Math.pow(distGender, 2));
    }

    public static Boolean writeByteArrayToPNG(byte[] bytes, String filename) {
        String outputPath = "src/main/resources/media/conversation/images/" + filename;

        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            fos.write(bytes);
            System.out.println("PNG image created successfully at: " + outputPath);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private Response becomeFriendsResponse(PreparedStatement preparedStatement, Request request)
            throws NoSuchFieldException,
            IllegalAccessException, SQLException, JsonParseException, JsonMappingException, IOException {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            Map<String, Integer> map = mapper.readValue(request.getRequestBody(),
                    new TypeReference<Map<String, Integer>>() {
                    });

            preparedStatement.setInt(1, map.get("sender"));
            preparedStatement.setInt(2, map.get("receiver"));
            preparedStatement.executeUpdate();
            String bodyResponse = String.format("{\"response\": \"%s\"}", "success");
            return new Response(request.getRequestId(), bodyResponse);

        } catch (Exception e) {
            String bodyResponse = String.format("{\"response\": \"%s\"}", "error");
            return new Response(request.getRequestId(), bodyResponse);
        }

    }

    private Response deleteInvitationResponse(PreparedStatement preparedStatement, Request request)
            throws NoSuchFieldException,
            IllegalAccessException, SQLException, JsonParseException, JsonMappingException, IOException {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            Map<String, Integer> map = mapper.readValue(request.getRequestBody(),
                    new TypeReference<Map<String, Integer>>() {
                    });

            preparedStatement.setInt(1, map.get("sender"));
            preparedStatement.setInt(2, map.get("receiver"));
            preparedStatement.executeUpdate();
            String bodyResponse = String.format("{\"response\": \"%s\"}", "success");
            return new Response(request.getRequestId(), bodyResponse);

        } catch (PSQLException e) {
            String bodyResponse = String.format("{\"response\": \"%s\"}", "error");
            return new Response(request.getRequestId(), bodyResponse);
        } catch (Exception e) {
            String bodyResponse = String.format("{\"response\": \"%s\"}", "error");
            return new Response(request.getRequestId(), bodyResponse);
        }

    }

    private Response InsertActivite(final PreparedStatement preparedStatement, final Request request)
            throws NoSuchFieldException, IllegalAccessException, JsonParseException, JsonMappingException, IOException {
        Response response = new Response();
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Activite activite = objectMapper.readValue(request.getRequestBody(), Activite.class);

            // PreparedStatement preparedStatement =
            // connection.prepareStatement(Queries.INSERT_ACTIVITY.query,
            // Statement.RETURN_GENERATED_KEYS);
            activite.build(preparedStatement);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    response.setResponseBody("{ \"student_id\": " + generatedId + " }");
                }
            }
            response.setRequestId(request.getRequestId());

            String responseBody = objectMapper.writeValueAsString(activite);

            // response.setResponseBody("{ \"student_id\": 123 }");

            System.out.println("Eloka" + responseBody + "Michel");
            System.out.println(response.getResponseBody());

            System.out.println(
                    "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

            response.setRequestId(request.getRequestId());

        } catch (SQLException e) {
            logger.error("Error handling INSERT_STUDENT request: {}", e.getMessage());

        }
        return response;
    }

    private Response SelectAllActivite(PreparedStatement preparedStatement)
            throws JsonProcessingException, NoSuchFieldException, IllegalAccessException {

        Response response = new Response();
        try {
            System.out.println(
                    "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
            // PreparedStatement preparedStatement =
            // connection.prepareStatement(Queries.SELECT_ALL_ACTIVITY.query);
            ResultSet resultSet = preparedStatement.executeQuery();

            Activites activites = new Activites();
            while (resultSet.next()) {
                Activite activite = new Activite().build(resultSet);
                activites.add(activite);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String responseBody = objectMapper.writeValueAsString(activites);
            System.out.println(responseBody);
            System.out.println(
                    "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

            response.setResponseBody(responseBody);
        } catch (SQLException e) {
            logger.error("Error handling SELECT_ALL_STUDENTS request: {}", e.getMessage());

        }
        return response;

    }

    private Response SelectMyActivite(final Request request, PreparedStatement preparedStatement)
            throws NoSuchFieldException, IllegalAccessException, IOException {

        Response response = new Response();
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Student student = objectMapper.readValue(request.getRequestBody(), Student.class);
            preparedStatement.setInt(1, student.getId_student());
            System.out.println(
                    "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
            // PreparedStatement preparedStatement =
            // connection.prepareStatement(Queries.SELECT_ALL_ACTIVITY.query);
            ResultSet resultSet = preparedStatement.executeQuery();

            Activites activites = new Activites();
            while (resultSet.next()) {
                Activite activite = new Activite().build(resultSet);
                activites.add(activite);
            }

            String responseBody = objectMapper.writeValueAsString(activites);
            System.out.println(responseBody);
            System.out.println(
                    "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

            response.setResponseBody(responseBody);
        } catch (SQLException e) {
            logger.error("Error handling SELECT_MY_STUDENTS request: {}", e.getMessage());

        }
        return response;

    }

    public Response selectStudentsResponse(PreparedStatement preparedStatement, Request request)
            throws SQLException, NoSuchFieldException, IllegalAccessException, JsonProcessingException {

        Students students = new Students();
        final ObjectMapper mapper = new ObjectMapper();
        String bodyResponse = "";

        ResultSet listOfStudents = preparedStatement.executeQuery();
        while (listOfStudents.next()) {
            Student student = new Student().build(listOfStudents);
            students.add(student);
        }
        listOfStudents.close();
        bodyResponse = mapper.writeValueAsString(students);
        return new Response(request.getRequestId(), bodyResponse);
    }

    public Response selectSelfInfoResponse(PreparedStatement preparedStatement, Request request)
            throws SQLException, NoSuchFieldException, IllegalAccessException, IOException {
        Student student = new Student();
        final ObjectMapper mapper = new ObjectMapper();
        String email = mapper.readValue(request.getRequestBody(), String.class);
        preparedStatement.setString(1, email);

        String bodyResponse = "";

        ResultSet listOfStudents = preparedStatement.executeQuery();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        while (listOfStudents.next()) {
            student = new Student().build(listOfStudents);
            student.setId_student(listOfStudents.getInt("id_student"));
            student.setProfileImage(listOfStudents.getString("profile_image"));
            InputStream inputStream = classLoader.getResourceAsStream("media/images/" + student.getProfileImage());
            student.setProfileImageStream(convertInputStreamToBytes(inputStream));
            break;
        }
        listOfStudents.close();
        bodyResponse = mapper.writeValueAsString(student);
        return new Response(request.getRequestId(), bodyResponse);
    }

    public Response selectFriendResponse(PreparedStatement preparedStatement, Request request) throws SQLException,
            JsonParseException, JsonMappingException, IOException, NoSuchFieldException, IllegalAccessException {
        BeFriends friends = new BeFriends();
        final ObjectMapper mapper = new ObjectMapper();
        final Student receiver = mapper.readValue(request.getRequestBody(), Student.class);
        preparedStatement.setInt(1, receiver.getId_student());
        String bodyResponse = "";

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        ResultSet listOfStudents = preparedStatement.executeQuery();
        while (listOfStudents.next()) {
            Student sender = new Student().build(listOfStudents);
            sender.buildUniversity(listOfStudents);
            sender.setProfileImage(listOfStudents.getString("profile_image"));
            InputStream inputStream = classLoader.getResourceAsStream("media/images/" + sender.getProfileImage());
            sender.setProfileImageStream(convertInputStreamToBytes(inputStream));
            BeFriend friend = new BeFriend().build(listOfStudents);
            friend.setSender(sender);
            friend.setReceiver(receiver);
            friends.add(friend);
            inputStream.close();
        }
        listOfStudents.close();
        bodyResponse = mapper.writeValueAsString(friends);
        return new Response(request.getRequestId(), bodyResponse);
    }

    public Response selectUniversitiesResponse(PreparedStatement preparedStatement, Request request)
            throws SQLException, NoSuchFieldException, IllegalAccessException, JsonProcessingException {

        Universities universities = new Universities();
        final ObjectMapper mapper = new ObjectMapper();
        String bodyResponse = "";

        ResultSet listOfUniversities = preparedStatement.executeQuery();
        while (listOfUniversities.next()) {
            University student = new University().build(listOfUniversities);
            universities.add(student);
        }
        listOfUniversities.close();
        bodyResponse = mapper.writeValueAsString(universities);
        return new Response(request.getRequestId(), bodyResponse);
    }

    public Response selectCitiesResponse(PreparedStatement preparedStatement, Request request)
            throws SQLException, NoSuchFieldException, IllegalAccessException, JsonProcessingException {

        Cities cities = new Cities();
        final ObjectMapper mapper = new ObjectMapper();
        String bodyResponse = "";

        ResultSet listOfCities = preparedStatement.executeQuery();
        while (listOfCities.next()) {
            City city = new City().build(listOfCities);
            cities.add(city);
        }
        listOfCities.close();
        bodyResponse = mapper.writeValueAsString(cities);
        return new Response(request.getRequestId(), bodyResponse);
    }

    public Response insertStudentResponse(PreparedStatement preparedStatement, Request request)
            throws JsonParseException, JsonMappingException, IOException, NoSuchFieldException, IllegalAccessException,
            SQLException {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final Student student = mapper.readValue(request.getRequestBody(), Student.class);
            int row = student.build(preparedStatement).executeUpdate();
            String errormsg = "L'etudiant existe deja ";
            String bodyResponse = String.format("{\"tester\": \"%s\"}", errormsg);
            return new Response(request.getRequestId(), bodyResponse);

        } catch (PSQLException e) {
            e.printStackTrace();
            String errormsg = "werrrr";
            String bodyResponse = String.format("{\"error\": \"%s\"}", errormsg);
            return new Response(request.getRequestId(), bodyResponse);
        }

    }

    public Response signinAsResponse(PreparedStatement preparedStatement, Request request)
            throws SQLException, NoSuchFieldException, IllegalAccessException, IOException {

        final ObjectMapper mapper = new ObjectMapper();
        final Student student = mapper.readValue(request.getRequestBody(), Student.class);
        preparedStatement.setString(1, student.getEmail());

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {

            final String hashedPassword = resultSet.getString("password");
            if (BCrypt.checkpw(student.getPassword(), hashedPassword)) {
                String bodyResponse = String.format("{\"msg\": \"%s\"}", "success");
                return new Response(request.getRequestId(), bodyResponse);
            }
            resultSet.close();
            String msg = "mot de passe incorrect";
            String bodyResponse = String.format("{\"msg\": \"%s\"}", msg);
            return new Response(request.getRequestId(), bodyResponse);

        } else {
            String errormsg = "l'utilisateur n'existe pas ";
            resultSet.close();
            String bodyResponse = String.format("{\"msg\": \"%s\"}", errormsg);
            return new Response(request.getRequestId(), bodyResponse);
        }

    }

    public Response getConnexionData(PreparedStatement preparedStatement, Request request)
            throws SQLException, NoSuchFieldException, IllegalAccessException, IOException {

        final ObjectMapper mapper = new ObjectMapper();
        final Student student = new Student();
        preparedStatement.setString(1, student.getEmail());

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            student.build(resultSet);
            String bodyResponse = mapper.writeValueAsString(student);
            resultSet.close();
            return new Response(request.getRequestId(), bodyResponse);

        } else {
            String errormsg = "l'utilisateur n'existe pas ";

            String bodyResponse = String.format("{\"msg\": \"%s\"}", errormsg);
            resultSet.close();
            return new Response(request.getRequestId(), bodyResponse);
        }

    }

    public Response doesStudentExistResponse(PreparedStatement preparedStatement, Request request)
            throws SQLException, NoSuchFieldException, IllegalAccessException, IOException {

        final ObjectMapper mapper = new ObjectMapper();
        final Student student = mapper.readValue(request.getRequestBody(), Student.class);
        preparedStatement.setString(1, student.getEmail());

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String errormsg = "user exist";
            resultSet.close();
            String bodyResponse = String.format("{\"msg\": \"%s\"}", errormsg);
            return new Response(request.getRequestId(), bodyResponse);
        } else {
            String errormsg = "success";
            resultSet.close();
            String bodyResponse = String.format("{\"msg\": \"%s\"}", errormsg);
            return new Response(request.getRequestId(), bodyResponse);
        }

    }

    private static byte[] convertInputStreamToBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[4096];

        int nRead;
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        return buffer.toByteArray();
    }

}
