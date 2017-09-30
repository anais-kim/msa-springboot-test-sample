package anais.springboot.sample.cafeteria.support;

import anais.springboot.sample.cafeteria.support.matcher.HamcrestMatcher;
import anais.springboot.sample.cafeteria.support.matcher.JsonObjectFactory;
import anais.springboot.sample.cafeteria.support.matcher.JsonType;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class HamcrestMatcherTest {

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
        JsonType expected = JsonObjectFactory.expected("{ \"firstName\": \"anais\"}");

        given().
            port(WIREMOCK_PORT).
        when().
            get("/object").
        then().
            body(HamcrestMatcher.match(expected));
    }

    @Test
    public void testJsonArrayMatcher() throws Exception {
        JsonType expected = JsonObjectFactory.expected("[1,2]");

        given().
            port(WIREMOCK_PORT).
        when().
            get("/array").
        then().
            body(HamcrestMatcher.match(expected));
    }

}
