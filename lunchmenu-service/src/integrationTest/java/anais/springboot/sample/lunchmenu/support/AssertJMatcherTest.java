package anais.springboot.sample.lunchmenu.support;

import anais.springboot.sample.lunchmenu.support.matcher.JsonObjectFactory;
import anais.springboot.sample.lunchmenu.support.matcher.JsonType;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import static anais.springboot.sample.lunchmenu.support.matcher.AssertJMatcher.assertThat;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.when;

public class AssertJMatcherTest {

    private static final int WIREMOCK_PORT = 5959;

    @ClassRule
    public static WireMockClassRule wiremock = new WireMockClassRule(WIREMOCK_PORT);

    @BeforeClass
    public static void init() throws Exception {
        RestAssured.reset();
        RestAssured.port = WIREMOCK_PORT;
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
        JsonType expected = JsonObjectFactory.expected("{ \"firstName\": \"anais\"}");

        Response res =  when().get("/object");
        assertThat(res).statusCode(200).match(expected);
    }

    @Test
    public void testJsonArrayMatcher() throws Exception {
        JsonType expected = JsonObjectFactory.expected("[1,2]");

        Response res = when().get("/array");
        assertThat(res).statusCode(200).match(expected);
    }

}
