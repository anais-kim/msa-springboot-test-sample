package anais.springboot.sample.lunchmenu.modules.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonObjectFactory {

    public static JsonType expected(Path path) {
        Object json = marshall(path);
        if (json instanceof Map) {
            return new JsonObject((Map) json, JsonState.EXPECTED);
        } else if (json instanceof ArrayList) {
            return new JsonArray((ArrayList) json, JsonState.EXPECTED);
        }
        throw new RuntimeException("Invalid Json Type...");
    }

    public static JsonType actual(String text) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Object json = objectMapper.readValue(text, Object.class);
            if (json instanceof Map) {
                return new JsonObject((Map) json, JsonState.ACTUAL);
            } else if (json instanceof ArrayList) {
                return new JsonArray((ArrayList) json, JsonState.ACTUAL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("fail to parse json file: "+text);
    }

    private static Object marshall(Path path){
        if(!Files.exists(path)){
            return new HashMap<>();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        try {
            return objectMapper.readValue(path.toFile(), Object.class);
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
}
