package edu.ssng.ing1.sirius.business.server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.business.dto.Students;
import edu.ssng.ing1.sirius.commons.Request;
import edu.ssng.ing1.sirius.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class XMartCityService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        SELECT_ALL_STUDENTS("SELECT t.name, t.firstname, t.group FROM \"ssn-db-ing1\".students t"),
        INSERT_STUDENT("INSERT into \"ssn-db-ing1\".students (\"name\", \"firstname\", \"group\") values (?, ?, ?)");

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

                case INSERT_STUDENT:
                    return insertStudentResponse(preparedStatement, request);
            }
            preparedStatement.close();

        } catch (IllegalArgumentException e) {
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

        return new Response("error","Requete impossible");
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

    public Response insertStudentResponse(PreparedStatement preparedStatement, Request request)
            throws JsonParseException, JsonMappingException, IOException, NoSuchFieldException, IllegalAccessException,
            SQLException {

        final ObjectMapper mapper = new ObjectMapper();
        final Student student = mapper.readValue(request.getRequestBody(), Student.class);
        int row = student.build(preparedStatement).executeUpdate();
        String bodyResponse = String.format("{\"student_id\": %d}", row);
        return new Response(request.getRequestId(), bodyResponse);
    }

}
