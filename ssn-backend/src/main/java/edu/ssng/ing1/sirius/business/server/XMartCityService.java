package edu.ssng.ing1.sirius.business.server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.sirius.business.dto.BeFriend;
import edu.ssng.ing1.sirius.business.dto.BeFriends;
import edu.ssng.ing1.sirius.business.dto.Cities;
import edu.ssng.ing1.sirius.business.dto.City;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

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
                case SELECT_ALL_STUDENTS:
                    return selectStudentsResponse(preparedStatement, request);

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
                case SIGN_IN_AS:
                    return signinAsResponse(preparedStatement, request);
                case STUDENT_INFO:
                    return selectSelfInfoResponse(preparedStatement, request);
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
