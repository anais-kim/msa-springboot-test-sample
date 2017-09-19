package anais.springboot.sample.cafeteria.modules;

import anais.springboot.sample.cafeteria.modules.json.JsonObjectFactory;
import anais.springboot.sample.cafeteria.modules.json.JsonType;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static anais.springboot.sample.cafeteria.modules.json.AssertJMatcher.assertThat;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class AssertJMatcherTest {

    private static final int WIREMOCK_PORT = 5959;

    @ClassRule
    public static WireMockClassRule wiremock = new WireMockClassRule(WIREMOCK_PORT);

    @BeforeClass
    public static void init() throws Exception {
        RestAssured.reset();
    }

    @BeforeClass
    public static void setUp() throws Exception {
        stubFor(get(urlEqualTo("/object"))
            .willReturn(okJson("{ \"firstName\": \"anais\", \"lastName\": \"Kim\"}")));

        stubFor(get(urlEqualTo("/array"))
            .willReturn(okJson("[1,2,3]")));
    }

    @Test
    public void testJsonObjectMatcher() throws Exception {
        Path tempFile = createTempFile("{ \"firstName\": \"anais\"}");
        JsonType expected = JsonObjectFactory.expected(tempFile);

        Response res = given().
                port(WIREMOCK_PORT).
            when().
                get("/object");

        assertThat(res).statusCode(200).match(expected);
    }

    @Test
    public void testJsonArrayMatcher() throws Exception {
        Path tempFile = createTempFile("[1,2]");
        JsonType expected = JsonObjectFactory.expected(tempFile);

        Response res = given().
            port(WIREMOCK_PORT).
        when().
            get("/array");

        assertThat(res).statusCode(200).match(expected);
    }

    private Path createTempFile(String content) throws IOException {
        Path tempFile = Files.createTempFile("", "");
        Files.write(tempFile, content.getBytes());
        return tempFile;
    }

}
