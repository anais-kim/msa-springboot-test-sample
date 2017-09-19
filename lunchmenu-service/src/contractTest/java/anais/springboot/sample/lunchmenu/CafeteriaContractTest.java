package anais.springboot.sample.lunchmenu;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureStubRunner(ids = {"anais.springboot.sample:cafeteria-service:+:stubs:6060"}, workOffline = true)
public class CafeteriaContractTest {

    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 6060;
        RestAssured.basePath = "/api";
    }

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

    @Test
    public void whenCallGetCafeteriaNonExist_thenReturnErrorInformation() throws Exception {
        given()
            .pathParam("cafeteriaId", 999)
        .when()
            .get("/cafeterias/{cafeteriaId}")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body("errorCode", notNullValue())
            .body("errorMessage", notNullValue());
    }

}
