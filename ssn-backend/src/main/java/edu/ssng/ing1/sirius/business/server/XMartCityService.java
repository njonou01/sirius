package edu.ssng.ing1.sirius.business.server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.BeFriend;
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
import java.util.zip.Deflater;

public class XMartCityService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        SELECT_ALL_CITIES("SELECT t.id_city , t.zipcode , t.city_name FROM \"ssn-db-ing1\".city t"),
        SELECT_ALL_STUDENTS(
                "SELECT familly_name, first_name, email, phone_number, gender, username, password, birthday\n" + //
                        "\tFROM \"ssn-db-ing1\".student"),
        SELECT_FRIENDS(
                "SELECT student.familly_name as familyname , student.first_name as firstname, student.email as email, student.phone_number as phoneNumber, student.gender as gender, student.username as username,student.profile_image as profile_image , student.password as password , student.birthday as bithday \n"
                        + //
                        "\tFROM \"ssn-db-ing1\".befriend as befriend inner join \"ssn-db-ing1\".student as student on befriend.receiver = student.id_student where befriend.sender = ? and befriend.status = 'accepted'"),

        SELECT_FRIEND_REQUEST_WITHOUT_ANSWER(
                "SELECT student.familly_name, student.first_name, student.email, student.phone_number, student.gender, student.username,student.profile_image\n"
                        + //
                        "\tFROM \"ssn-db-ing1\".befriend as befriend inner join \"ssn-db-ing1\".student as student on befriend.receiver = student.id_student where befriend.sender=2 and befriend.status = 'no reponse'"),
        SELECT_ALL_UNIVERSITIES("SELECT t.id_university, t.label, t.shortname, t.acronym\n" + //
                "\tFROM \"ssn-db-ing1\".university t ;"),
        DOES_STUDENT_EXIST(
                "SELECT familly_name, first_name, email, phone_number, gender, username, password, birthday\n" + //
                        "\tFROM \"ssn-db-ing1\".student where email = ? "),
        INSERT_STUDENT(
                "INSERT INTO \"ssn-db-ing1\".student (familly_name, first_name, email, phone_number, gender, username, \"password\", birthday) VALUES(?, ?, ?, ?, ?, ?, ? , ?)"),
        SIGN_IN_AS(
                "SELECT  password FROM \"ssn-db-ing1\".student where email=? ");

        private final String query;

        private Queries(final String query) {
            this.query = query;
        }

        public String getQuery() {
            return query;
        }
    }

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

    public Response selectFriendResponse(PreparedStatement preparedStatement, Request request) throws SQLException,
            JsonParseException, JsonMappingException, IOException, NoSuchFieldException, IllegalAccessException {
        Students students = new Students();
        final ObjectMapper mapper = new ObjectMapper();
        final BeFriend friend_relation = mapper.readValue(request.getRequestBody(), BeFriend.class);
        preparedStatement.setInt(1,1);
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
            String errormsg = "noonnnnnnnnnn c'a n'a pas fonctionné";
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
