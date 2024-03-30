package edu.ssng.ing1.sirius.business.server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ssng.ing1.City;
import edu.ssng.ing1.sirius.business.dto.Cities;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.business.dto.Students;
import edu.ssng.ing1.sirius.business.dto.Universities;
import edu.ssng.ing1.sirius.business.dto.University;
import edu.ssng.ing1.sirius.commons.Request;
import edu.ssng.ing1.sirius.commons.Response;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class XMartCityService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        SELECT_ALL_CITIES("SELECT t.id_city , t.zipcode , t.city_name FROM \"ssn-db-ing1\".city t"),
        SELECT_ALL_STUDENTS(
                "SELECT familly_name, first_name, email, phone_number, gender, username, password, birthday\n" + //
                        "\tFROM \"ssn-db-ing1\".student"),
        SELECT_ALL_UNIVERSITIES("SELECT t.id_university, t.label, t.shortname, t.acronym\n" + //
                "\tFROM \"ssn-db-ing1\".university t ;"),
        DOES_STUDENT_EXIST(
                "SELECT familly_name, first_name, email, phone_number, gender, username, password, birthday\n" + //
                        "\tFROM \"ssn-db-ing1\".student where username = ? "),
        INSERT_STUDENT(
                "INSERT INTO \"ssn-db-ing1\".student (familly_name, first_name, email, phone_number, gender, username, \"password\", birthday) VALUES(?, ?, ?, ?, ?, ?, ? , ?)");

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

                case SELECT_ALL_UNIVERSITIES:
                    return selectUniversitiesResponse(preparedStatement, request);

                case INSERT_STUDENT:
                    return insertStudentResponse(preparedStatement, request);
                case DOES_STUDENT_EXIST:
                    return doesStudentExistResponse(preparedStatement, request);

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

    public Response doesStudentExistResponse(PreparedStatement preparedStatement, Request request)
            throws SQLException, NoSuchFieldException, IllegalAccessException, IOException {

        final ObjectMapper mapper = new ObjectMapper();
        final Student student = mapper.readValue(request.getRequestBody(), Student.class);
        preparedStatement.setString(1, student.getUsername());

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String errormsg = "success";
            resultSet.close();
            String bodyResponse = String.format("{\"msg\": \"%s\"}", errormsg);
            return new Response(request.getRequestId(), bodyResponse);

        } else {
            String errormsg = "noonnnnnnnnnn c'a n'a pas fonctionné";
            resultSet.close();
            String bodyResponse = String.format("{\"msg\": \"%s\"}", errormsg);
            return new Response(request.getRequestId(), bodyResponse);
        }

    }

}
