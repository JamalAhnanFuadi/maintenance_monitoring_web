package id.tsi.mmw.rest.feature;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

@Provider
public class JacksonFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        context.register(getJsonProvider());
        return true;
    }

    private JacksonJaxbJsonProvider getJsonProvider() {
        return new JacksonJaxbJsonProvider(getObjectMapper(), JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
    }

    private ObjectMapper getObjectMapper() {
        // Create a new instance of ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Configure the ObjectMapper to not fail when encountering unknown properties during deserialization
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Set the behavior to exclude properties with null values during serialization
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Enable indentation for the output during serialization
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Return the configured ObjectMapper
        return objectMapper;
    }
}
