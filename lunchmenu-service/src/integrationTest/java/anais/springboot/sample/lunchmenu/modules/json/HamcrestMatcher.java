package anais.springboot.sample.lunchmenu.modules.json;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.nio.file.Path;

public class HamcrestMatcher extends TypeSafeDiagnosingMatcher<String> {

    private JsonType expected;

    public HamcrestMatcher(JsonType expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(String actualtext, Description description) {
        JsonType actual = JsonObjectFactory.actual(actualtext);
        return _isValid(expected, actual);
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expected);
    }

    private boolean _isValid(JsonType expected, JsonType actual) {
        if(expected instanceof JsonObject && actual instanceof  JsonObject){
            JsonObject e = (JsonObject) expected;
            JsonObject a = (JsonObject) actual;
            return a.entrySet().containsAll(e.entrySet());
        }

        if(expected instanceof JsonArray && actual instanceof  JsonArray){
            JsonArray e = (JsonArray) expected;
            JsonArray a = (JsonArray) actual;
            return a.containsAll(e);
        }

        return false;
    }

    public static HamcrestMatcher match(JsonType expected){
        return new HamcrestMatcher(expected);
    }

    public static HamcrestMatcher match(Path expectedPath){
        JsonType expected = JsonObjectFactory.expected(expectedPath);
        return new HamcrestMatcher(expected);
    }
}
