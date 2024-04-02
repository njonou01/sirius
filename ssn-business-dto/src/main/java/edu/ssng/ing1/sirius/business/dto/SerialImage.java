package edu.ssng.ing1.sirius.business.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SerialImage {

    // Serialize InputStream to JSON string
    public static String serialize(InputStream inputStream) throws IOException {
        BufferedImage image = ImageIO.read(inputStream);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        return bytesToJSON(bytes);
    }

    // Deserialize JSON string to BufferedImage
    public static BufferedImage deserialize(String json) throws IOException {
        byte[] bytes = jsonToBytes(json);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        return ImageIO.read(bais);
    }

    // Helper method to convert byte array to JSON string
    private static String bytesToJSON(byte[] bytes) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(bytes);
    }

    // Helper method to convert JSON string to byte array
    private static byte[] jsonToBytes(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, byte[].class);
    }


}
