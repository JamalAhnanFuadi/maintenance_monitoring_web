package id.tsi.mmw.util.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.inject.Singleton;

@Singleton
public class CustomObjectMapper {

    private static CustomObjectMapper instance;

    private ObjectMapper objectMapper;

    private CustomObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static CustomObjectMapper getInstance() {
        if (instance == null) {
            instance = new CustomObjectMapper();
        }
        return instance;

    }

}
