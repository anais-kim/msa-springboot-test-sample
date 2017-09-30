package anais.springboot.sample.cafeteria;

import anais.springboot.sample.cafeteria.json.*;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static anais.springboot.sample.cafeteria.modules.json.HamcrestMatcher.match;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class CafeteriaIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void whenGetAllCafeterias_thenReturnFourJsonArray() throws Exception {
        when()
            .get("/cafeterias")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(match(GetAllCafeteriaSuccessResponse.json));
    }

    @Test
    public void whenGetCafeteria_thenReturnJsonObject() throws Exception {
        when()
            .get("/cafeterias/1")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(match(GetCafeteriaSuccessResponse.json));
    }

    @Test
    public void whenAddCafeteria_thenReturnCafeteria() throws Exception {
        given()
            .body(AddCafeteriaSuccessRequest.json)
        .when()
            .post("/cafeterias")
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .body(match(AddCafeteriaSuccessResponse.json));
    }

    @Test
    public void whenAddCafeteriaAlreadyExist_thernReturnError() throws Exception {
        given()
            .body(AddCafeteriaErrorDuplicateRequest.json)
        .when()
            .post("/cafeterias")
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body(match(AddCafeteriaErrorDuplicateResponse.json));
    }

}
