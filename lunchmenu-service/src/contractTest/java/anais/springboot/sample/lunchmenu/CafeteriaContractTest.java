package anais.springboot.sample.lunchmenu;

import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CafeteriaContractTest extends AbstractContractTest {

    @Test
    public void whenCallGetCafeteriaExist_thenReturnCafeteriaInformation() throws Exception {
        given()
            .pathParam("cafeteriaId", 4)
        .when()
            .get("/api/cafeterias/{cafeteriaId}")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body("id", is(4));
    }

    @Test
    public void whenCallGetCafeteriaNonExist_thenReturnErrorInformation() throws Exception {
        given()
            .pathParam("cafeteriaId", 999)
        .when()
            .get("/api/cafeterias/{cafeteriaId}")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body("errorCode", notNullValue())
            .body("errorMessage", notNullValue());
    }

}
