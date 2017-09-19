package anais.springboot.sample.lunchmenu;

import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class CafeteriaContractTest extends AbstractContractTest {

    @Test
    public void whenCallGetCafeteriaExist_thenReturnCafeteriaInformation() throws Exception {
        given()
            .pathParam("cafeteriaId", 4)
        .when()
            .get("/cafeterias/{cafeteriaId}")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body("id", is(4));
    }

}
