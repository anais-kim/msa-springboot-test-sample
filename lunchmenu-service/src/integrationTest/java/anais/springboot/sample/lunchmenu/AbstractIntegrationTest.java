package anais.springboot.sample.lunchmenu;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {

    private static final String SERVER_URL = "http://localhost";
    private static final String BASE_PATH = "/api";

    @ClassRule
    public static WireMockClassRule wiremock = new WireMockClassRule(5959);

    @LocalServerPort
    int port;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void initializeObjectMapper() throws Exception {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    }

    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = this.port;
        RestAssured.basePath = BASE_PATH;
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

}