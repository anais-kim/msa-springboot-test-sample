package anais.springboot.sample.cafeteria.support.matcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Map;

public class JsonArray extends ArrayList implements JsonType{

    private final JsonState jsonState;

    public JsonArray(ArrayList list, JsonState jsonState) {
        this.jsonState = jsonState;
        for(Object value : list){
            if(value instanceof Map) {
                this.add(new JsonObject((Map)value, jsonState));
            }else if(value instanceof ArrayList){
                this.add(new JsonArray((ArrayList)value, jsonState));
            }else{
                this.add(value);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof JsonArray)){
            return false;
        }
        JsonArray expected = this;
        JsonArray actual = (JsonArray)o;
        if(actual.getJsonState() == JsonState.EXPECTED){
            expected = (JsonArray)o;
            actual = this;
        }
        return actual.containsAll(expected);
    }

    @Override
    public JsonState getJsonState() {
        return this.jsonState;
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
