package anais.springboot.sample.lunchmenu;

import com.google.common.io.Resources;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static anais.springboot.sample.lunchmenu.modules.json.HamcrestMatcher.match;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class LunchMenuIntegrationTest extends AbstractIntegrationTest {

    private static Path GETALL_SUCCESS_RESPONSE;
    private static Path GET_SUCCESS_RESPONSE;
    private static Path ADD_SUCCESS_REQUEST;
    private static Path ADD_SUCCESS_RESPONSE;
    private static Path ADD_ERROR_DUPLICATED_REQUEST;
    private static Path ADD_ERROR_DUPLICATED_RESPONSE;
    private static Path ADD_ERROR_CAFETERIA_REQUEST;
    private static Path ADD_ERROR_CAFETERIA_RESPONSE;

    @BeforeClass
    public static void init() throws Exception {
        GETALL_SUCCESS_RESPONSE = getResource("get_all_success_response.json");
        GET_SUCCESS_RESPONSE = getResource("get_success_response.json");
        ADD_SUCCESS_REQUEST = getResource("add_success_request.json");
        ADD_SUCCESS_RESPONSE = getResource("add_success_response.json");
        ADD_ERROR_DUPLICATED_REQUEST = getResource("add_error_duplicated_request.json");
        ADD_ERROR_DUPLICATED_RESPONSE = getResource("add_error_duplicated_response.json");
        ADD_ERROR_CAFETERIA_REQUEST = getResource("add_error_cafeteria_request.json");
        ADD_ERROR_CAFETERIA_RESPONSE = getResource("add_error_cafeteria_response.json");
    }

    @Before
    public void stubCafeteriaService() throws Exception {
        stubFor(get(urlEqualTo("/api/cafeterias/4")).atPriority(1)
            .willReturn(ok()));

        stubFor(get(urlPathMatching("/api/cafeterias/.*")).atPriority(2)
            .willReturn(notFound()));
    }

    @Test
    public void whenGetAllLunchMenus_thenReturnThreeJsonArray() throws Exception {
        when()
            .get("/lunchMenus")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(match(GETALL_SUCCESS_RESPONSE));
    }

    @Test
    public void whenGetLunchMenu_thenReturnJsonObject() throws Exception {
        when()
            .get("/lunchMenus/1")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(match(GET_SUCCESS_RESPONSE));
    }

    @Test
    public void whenAddLunchMenu_thenReturnLunchMenu() throws Exception {
        given()
            .contentType(ContentType.JSON)
            .body(ADD_SUCCESS_REQUEST.toFile())
        .when()
            .post("/lunchMenus")
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .body(match(ADD_SUCCESS_RESPONSE));
    }

    @Test
    public void whenAddLunchMenuAlreadyExist_thernReturnError() throws Exception {
        given()
            .contentType(ContentType.JSON)
            .body(ADD_ERROR_DUPLICATED_REQUEST.toFile())
        .when()
            .post("/lunchMenus")
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body(match(ADD_ERROR_DUPLICATED_RESPONSE));
    }

    @Test
    public void whenAddLunchMenuWithCafeteriaNotExist_thenReturnError() throws Exception {
        given()
            .contentType(ContentType.JSON)
            .body(ADD_ERROR_CAFETERIA_REQUEST.toFile())
        .when()
            .post("/lunchMenus")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body(match(ADD_ERROR_CAFETERIA_RESPONSE));
    }

    private static Path getResource(String location) throws URISyntaxException {
        String directory = "json/lunchmenu/";
        return Paths.get(Resources.getResource(directory + location).toURI());
    }

}
