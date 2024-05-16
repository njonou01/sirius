package edu.ssng.ing1.sirius.business.server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.business.dto.BeFriend;
import edu.ssng.ing1.sirius.business.dto.BeFriends;
import edu.ssng.ing1.sirius.business.dto.Activite;
import edu.ssng.ing1.sirius.business.dto.Activites;
import edu.ssng.ing1.sirius.business.dto.Cities;
import edu.ssng.ing1.sirius.business.dto.City;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.business.dto.Students;
import edu.ssng.ing1.sirius.business.dto.Universities;
import edu.ssng.ing1.sirius.business.dto.University;
import edu.ssng.ing1.sirius.business.server.notifyProcess.StudentConnectedProcess;
import edu.ssng.ing1.sirius.commons.Request;
import edu.ssng.ing1.sirius.commons.Response;
import edu.ssng.ing1.sirius.commons.SomeInfo;

import org.mindrot.jbcrypt.BCrypt;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

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
                case DISCONNECTION_STUDENT:

                    return disconnection(request, preparedStatement, connection);
                case ACTIVITY_INVITATION:

                    return activityInvitation(preparedStatement, request, connection);
                case SELECT_MY_ACTIVITY:

                    return SelectMyActivite(request, preparedStatement);
                case SELECT_ALL_STUDENTS:

                    return selectStudentsResponse(preparedStatement, request);
                case SELECT_MY_FRIENDS:

                    return selectMyfriends(preparedStatement, request);

                case INSERT_ACTIVITY:
                    return InsertActivite(preparedStatement, request, connection);

                case SELECT_ALL_CITIES:
                    return selectCitiesResponse(preparedStatement, request);

                case SELECT_FRIEND_REQUEST_WITHOUT_ANSWER:
                    return selectStudentsResponse(preparedStatement, request);

                case SELECT_FRIENDS:
                    return selectFriendResponse(preparedStatement, request);

                case SELECT_ALL_UNIVERSITIES:
                    return selectUniversitiesResponse(preparedStatement, request);
                case SELECT_LAST_ACTIVITY_FRIENDS:
                    return selectLastActivityFriends(preparedStatement, request);

                case INSERT_STUDENT:
                    return insertStudentResponse(preparedStatement, request);
                case DOES_STUDENT_EXIST:
                    return doesStudentExistResponse(preparedStatement, request);
                case SIGN_IN_AS:
                    return signinAsResponse(preparedStatement, request);
                case STUDENT_INFO:
                    return selectSelfInfoResponse(preparedStatement, request, connection);
                case GET_DATA_CONNEXION:
                    return getConnexionData(preparedStatement, request);
                default:
                    break;
            }
            preparedStatement.close();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + " --> " + e.getClass());
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " --> " + e.getClass());
        } catch (NoSuchFieldException e) {
            System.out.println(e.getMessage() + " --> " + e.getClass());
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage() + " --> " + e.getClass());
        } catch (IOException e) {
            System.out.println(e.getMessage() + " --> " + e.getClass());
        }

        return new Response("error", "Requete impossible");
    }

    private Response activityInvitation(final PreparedStatement preparedStatement, final Request request,
            Connection connection) throws SQLException {
        int rows = preparedStatement.executeUpdate();
        Response response = new Response();
        return response;

    }

    private Response InsertActivite(final PreparedStatement preparedStatement, final Request request,
            Connection connection)
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
            Set<Student> students = activite.getStudents();
            Set<String> emailToSend = new HashSet<>();

            for (Student student : students) {
                emailToSend.add(student.getEmail());

            }

            System.out.println(
                    "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
            BroadcastNotification.broadcast("NEW_ACTIVITY",
                    StudentConnectedProcess.getFriends(activite.getEmailCreateur(),
                            connection),
                    activite, activite.getNomCreateur(), activite.getNom_interet_activite());
            response.setRequestId(request.getRequestId());
            BroadcastNotification.broadcast("INVITE_ACTIVITY", emailToSend, activite,activite.getNomCreateur(),
                    activite.getNom_interet_activite());
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
   
            ResultSet resultSet = preparedStatement.executeQuery();

            Activites activites = new Activites();
            while (resultSet.next()) {
                Activite activite = new Activite().build(resultSet);
                activites.add(activite);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String responseBody = objectMapper.writeValueAsString(activites);
            System.out.println(responseBody);
           
            response.setResponseBody(responseBody);
        } catch (SQLException e) {
            logger.error("Error handling SELECT_ALL_STUDENTS request: {}", e.getMessage());

        }
        return response;

    }

    private Response disconnection(Request request, PreparedStatement preparedStatement, Connection connection) {
        Response response = new Response();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Student student = objectMapper.readValue(request.getRequestBody(), Student.class);
            SomeInfo someInfo = new SomeInfo();
            someInfo.setInfo(student.getEmail());
            ConnectedStudent.removeNewStudentConnected(someInfo);
            BroadcastNotification.broadcast("DISCONNECTION_STUDENT",
                    StudentConnectedProcess.getFriends(someInfo.getInfo(),
                            connection),
                    someInfo.getInfo());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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

    public Response selectMyfriends(PreparedStatement preparedStatement, Request request)
            throws SQLException, NoSuchFieldException, IllegalAccessException, IOException {

        Students students = new Students();
        final ObjectMapper mapper = new ObjectMapper();
        Student stud = mapper.readValue(request.getRequestBody(), Student.class);
        preparedStatement.setString(1, stud.getEmail().trim());
        preparedStatement.setString(2, stud.getEmail().trim());
        preparedStatement.setString(3, stud.getEmail().trim());

        ResultSet listOfStudents = preparedStatement.executeQuery();
        while (listOfStudents.next()) {
            Student student = new Student();
            // student.setFamilyname(listOfStudents.getString(2));
            student.setEmail(listOfStudents.getString("email"));
            student.setFirstname(listOfStudents.getString("first_name"));
            student.setFamilyname(listOfStudents.getString("familly_name"));

            // student.setFirstname(listOfStudents.getString(0));
            students.add(student);
        }
        listOfStudents.close();
        String bodyResponse = mapper.writeValueAsString(students);
        return new Response(request.getRequestId(), bodyResponse);
    }

    public Response selectLastActivityFriends(PreparedStatement preparedStatement, Request request)
            throws SQLException, NoSuchFieldException, IllegalAccessException, IOException {

        Students students = new Students();
        final ObjectMapper mapper = new ObjectMapper();
        String bodyResponse = "";
        Student stud = mapper.readValue(request.getRequestBody(), Student.class);

        preparedStatement.setString(1, stud.getEmail().trim());
        ResultSet listOfStudents = preparedStatement.executeQuery();
        while (listOfStudents.next()) {
             Student student = new Student();
             System.out.println(
                "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        
             student.setEmail(listOfStudents.getString("email"));
            student.setFirstname(listOfStudents.getString("first_name"));
            student.setFamilyname(listOfStudents.getString("familly_name"));
            students.add(student);
            System.out.println(listOfStudents);
        }
        listOfStudents.close();
        bodyResponse = mapper.writeValueAsString(students);
        return new Response(request.getRequestId(), bodyResponse);
    }

    public Response selectSelfInfoResponse(PreparedStatement preparedStatement, Request request, Connection connection)
            throws SQLException, NoSuchFieldException, IllegalAccessException, IOException {
        Student student = new Student();
        final ObjectMapper mapper = new ObjectMapper();
        SomeInfo someInfo = mapper.readValue(request.getRequestBody(), SomeInfo.class);
        preparedStatement.setString(1, someInfo.getInfo());

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
        // Student studentt= new Student();
        // studentt.setEmail("elokamichel@gmail.com");
        // Set<String> set = new HashSet<>();
        // // set.add("kshemwell0@4shared.com");
        ConnectedStudent.addNewStudentConnected(someInfo);
        BroadcastNotification.broadcast("NEW_CONNECTION",
                StudentConnectedProcess.getFriends(someInfo.getInfo(),
                        connection),
                someInfo.getInfo());
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
                System.out.println(ConnectedStudent.getStudentConnectedemailHashmap());
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

}
