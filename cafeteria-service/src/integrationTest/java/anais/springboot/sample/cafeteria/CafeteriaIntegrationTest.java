package anais.springboot.sample.cafeteria;

import com.google.common.io.Resources;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static anais.springboot.sample.cafeteria.modules.json.HamcrestMatcher.match;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class CafeteriaIntegrationTest extends AbstractIntegrationTest {

    private static Path GETALL_SUCCESS_RESPONSE;
    private static Path GET_SUCCESS_RESPONSE;
    private static Path ADD_SUCCESS_REQUEST;
    private static Path ADD_SUCCESS_RESPONSE;
    private static Path ADD_ERROR_DUPLICATED_REQUEST;
    private static Path ADD_ERROR_DUPLICATED_RESPONSE;

    @BeforeClass
    public static void init() throws Exception {
        GETALL_SUCCESS_RESPONSE = getResource("get_all_success_response.json");
        GET_SUCCESS_RESPONSE = getResource("get_success_response.json");
        ADD_SUCCESS_REQUEST = getResource("add_success_request.json");
        ADD_SUCCESS_RESPONSE = getResource("add_success_response.json");
        ADD_ERROR_DUPLICATED_REQUEST = getResource("add_error_duplicated_request.json");
        ADD_ERROR_DUPLICATED_RESPONSE = getResource("add_error_duplicated_response.json");
    }

    @Test
    public void whenGetAllCafeterias_thenReturnFourJsonArray() throws Exception {
        when()
            .get("/cafeterias")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(match(GETALL_SUCCESS_RESPONSE));
    }

    @Test
    public void whenGetCafeteria_thenReturnJsonObject() throws Exception {
        when()
            .get("/cafeterias/1")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(match(GET_SUCCESS_RESPONSE));
    }

    @Test
    public void whenAddCafeteria_thenReturnCafeteria() throws Exception {
        given()
            .contentType(ContentType.JSON)
            .body(ADD_SUCCESS_REQUEST.toFile())
        .when()
            .post("/cafeterias")
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .body(match(ADD_SUCCESS_RESPONSE));
    }

    @Test
    public void whenAddCafeteriaAlreadyExist_thernReturnError() throws Exception {
        given()
            .contentType(ContentType.JSON)
            .body(ADD_ERROR_DUPLICATED_REQUEST.toFile())
        .when()
            .post("/cafeterias")
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body(match(ADD_ERROR_DUPLICATED_RESPONSE));
    }

    private static Path getResource(String location) throws URISyntaxException {
        String directory = "json/cafeteria/";
        return Paths.get(Resources.getResource(directory + location).toURI());
    }

}
