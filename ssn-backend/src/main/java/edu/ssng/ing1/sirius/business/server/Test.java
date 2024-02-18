package edu.ssng.ing1.sirius.business.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {
    public static void main(String[] args) throws JsonProcessingException {
        String jsonString = String.format("{\"student_id\": %d}", 5 );
        System.out.println(jsonString);
        ObjectMapper objectMapper2 = new ObjectMapper();
        final String studentJson2 = objectMapper2.writeValueAsString(jsonString);
        System.out.println(studentJson2);


    }
    
}
