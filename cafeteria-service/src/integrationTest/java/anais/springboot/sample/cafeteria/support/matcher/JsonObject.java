package anais.springboot.sample.cafeteria.support.matcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonObject extends HashMap implements JsonType{

    private final JsonState jsonState;

    public JsonObject(Map map, JsonState jsonState) {
        this.jsonState = jsonState;
        map.forEach((key, value) -> {
            if(value instanceof Map) {
                this.put(key, new JsonObject((Map)value, jsonState));
            }else if(value instanceof ArrayList){
                this.put(key, new JsonArray((ArrayList)value, jsonState));
            }else{
                this.put(key, value);
            }
        });
    }

    @Override
    public JsonState getJsonState() {
        return this.jsonState;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof JsonObject)){
            return false;
        }
        JsonObject expected = this;
        JsonObject actual = (JsonObject)o;
        if(actual.getJsonState() == JsonState.EXPECTED){
            expected = (JsonObject)o;
            actual = this;
        }
        return actual.entrySet().containsAll(expected.entrySet());
    }

    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
