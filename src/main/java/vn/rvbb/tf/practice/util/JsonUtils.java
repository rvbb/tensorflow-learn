package vn.rvbb.tf.practice.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {
    public static final ObjectMapper MAPPER = new ObjectMapper();
    public static final ObjectMapper PRETTY_MAPPER = new ObjectMapper();
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        PRETTY_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static byte[] json(Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsBytes(object);
    }

    public static String asString(Object object) throws JsonProcessingException {
        return (object != null) ? MAPPER.writeValueAsString(object) : "NULL";
    }

    public static String marshalJsonAsPrettyString(Object object) throws JsonProcessingException {
        return (object != null) ? PRETTY_MAPPER.writeValueAsString(object) : "NULL";
    }

    public static <T> T unJson(byte[] json, Class<T> type) {
        int length = 0;
        try {
            length = json.length;
            return MAPPER.readValue(json, type);
        } catch (Exception e) {
            log.error("Unable to parse json from a array of bytes(length={}) to a object={}. Cause by ={} ", length,
                    type, e);
            return null;
        }
    }

    public static <T> byte[] serialize(T object) throws IOException {
        return objectMapper.writeValueAsBytes(object);
    }

    public static <T> T deserialize(byte[] json, TypeReference<T> tClass) throws IOException {
        return objectMapper.readValue(json, tClass);
    }

    public static <T> T map2Object(@SuppressWarnings("rawtypes") Map map, TypeReference<T> o) throws IOException {
        return objectMapper.convertValue(map, o);
    }

    public static String serializeMap(Map<Object, Object> map) throws IOException {
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(map);
    }

    public static Map<Object, Object> deserializeMap(String json) throws IOException {
        return MAPPER.readValue(json, new TypeReference<HashMap<Object, Object>>() {});
    }
}
