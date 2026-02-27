package uk.co.ryanharrison.crudapi.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jspecify.annotations.NullMarked;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

@NullMarked
public class JsonUtils {

    public static final JsonMapper MAPPER = JsonMapper.builder()
            .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL))
            .changeDefaultPropertyInclusion(incl -> incl.withContentInclusion(JsonInclude.Include.NON_NULL))
            .configure(SerializationFeature.INDENT_OUTPUT, true)
            .build();

    public static <T> T readObject(String json, Class<T> clazz) {
        return MAPPER.readValue(json, clazz);
    }

    public static String writeValue(Object object) {
        return MAPPER.writeValueAsString(object);
    }
}
