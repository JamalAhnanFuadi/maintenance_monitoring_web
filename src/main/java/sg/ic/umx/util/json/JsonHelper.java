package sg.ic.umx.util.json;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import sg.ic.umx.util.log.BaseLogger;

public class JsonHelper {

    private static final BaseLogger log = new BaseLogger(JsonHelper.class);
    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private static final String FROM_JSON = "fromJson";

    private JsonHelper() {}

    public static ObjectMapper getMapper() {
        return OBJECT_MAPPER;
    }

    public static String toJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("toJson", e);
        }
        return "";
    }

    public static String toJsonUTF(Object obj) {
        try {
            byte[] data = OBJECT_MAPPER.writeValueAsBytes(obj);

            return new String(data, "UTF-8");
        } catch (JsonProcessingException e) {
            log.error("toJsonUTF", e);
        } catch (UnsupportedEncodingException e) {
            log.error("toJsonUTF", e);
        }
        return "";
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            log.error(FROM_JSON, e);
        }

        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception ex) {
            log.error(FROM_JSON, "Could not invoke Default Constructor", ex);
        }

        return null;
    }

    public static <T> T fromJson(String json, TypeReference<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            log.error(FROM_JSON, e);
        }
        return null;
    }
}
