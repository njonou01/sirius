package edu.ssng.ing1.sirius.business.server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.BeFriend;
import edu.ssng.ing1.sirius.business.dto.Activite;
import edu.ssng.ing1.sirius.business.dto.Activites;
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
                case SELECT_ALL_ACTIVITY:

                    return SelectAllActivite(connection, preparedStatement);
                    

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
                case SIGN_IN_AS:
                    return signinAsResponse(preparedStatement, request);
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

    private Response SelectAllActivite(final Connection connection, PreparedStatement preparedStatement) throws JsonProcessingException, NoSuchFieldException, IllegalAccessException {

        Response response = new Response();
    try {
        System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        // PreparedStatement preparedStatement = connection.prepareStatement(Queries.SELECT_ALL_ACTIVITY.query);
        ResultSet resultSet = preparedStatement.executeQuery();

        
        Activites activites = new Activites();
        while (resultSet.next()) {
            Activite activite = new Activite().build(resultSet);
            activites.add(activite);
        }

        
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(activites);
        System.out.println(responseBody);
        System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");


        
        response.setResponseBody(responseBody);
    } catch (SQLException e) {
        logger.error("Error handling SELECT_ALL_STUDENTS request: {}", e.getMessage());
        
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

    public Response selectFriendResponse(PreparedStatement preparedStatement, Request request) throws SQLException,
            JsonParseException, JsonMappingException, IOException, NoSuchFieldException, IllegalAccessException {
        Students students = new Students();
        final ObjectMapper mapper = new ObjectMapper();
        final BeFriend friend_relation = mapper.readValue(request.getRequestBody(), BeFriend.class);
        preparedStatement.setInt(1, friend_relation.getSender().getId_student());
        String bodyResponse = "";

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        ResultSet listOfStudents = preparedStatement.executeQuery();
        while (listOfStudents.next()) {
            Student student = new Student().build(listOfStudents);
            student.setProfileImage(listOfStudents.getString("profile_image"));
            InputStream inputStream = classLoader.getResourceAsStream("media/images/" + student.getProfileImage());
            student.setProfileImageStream(convertInputStreamToBytes(inputStream));
            students.add(student);
            inputStream.close();
        }
        listOfStudents.close();
        bodyResponse = mapper.writeValueAsString(students);
        File file = new File("montest.json");

        mapper.writeValue(file, bodyResponse);
        return new Response(request.getRequestId(), bodyResponse);
    }

    private static byte[] convertInputStreamToBytes(InputStream inputStream) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
