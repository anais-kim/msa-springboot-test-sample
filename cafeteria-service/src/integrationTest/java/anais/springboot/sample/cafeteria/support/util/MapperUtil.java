package anais.springboot.sample.cafeteria.support.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;

public class MapperUtil {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    }

    public static <T> T marshall(String content, Class<T> valueType) {
        try {
            return mapper.readValue(content, valueType);
        } catch (IOException e) {
            throw new RuntimeException("Fail to marshall to "+valueType.getSimpleName()+": "+content);
        }
    }

    public static <T> T marshall(Path content, Class<T> valueType) {
        try {
            return mapper.readValue(content.toFile(), valueType);
        } catch (IOException e) {
            throw new RuntimeException("Fail to marshall to "+valueType.getSimpleName()+": "+content);
        }
    }

}