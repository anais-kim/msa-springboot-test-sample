package anais.springboot.sample.cafeteria.support.matcher;

import anais.springboot.sample.cafeteria.support.util.MapperUtil;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

public class JsonObjectFactory {

    public static JsonType expected(String content) {
        Object json = MapperUtil.marshall(content, Object.class);
        return create(json, JsonState.EXPECTED);
    }
    public static JsonType expected(Path content) {
        Object json = MapperUtil.marshall(content, Object.class);
        return create(json, JsonState.EXPECTED);
    }
    public static JsonType actual(String content) {
        Object json = MapperUtil.marshall(content, Object.class);
        return create(json, JsonState.ACTUAL);
    }

    private static JsonType create(Object json, JsonState jsonState) {
        if (json instanceof Map) {
            return new JsonObject((Map) json, jsonState);
        } else if (json instanceof ArrayList) {
            return new JsonArray((ArrayList) json, jsonState);
        }
        throw new RuntimeException("Invalid Json Type.");
    }

}
