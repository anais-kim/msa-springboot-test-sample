package anais.springboot.sample.lunchmenu;

import anais.springboot.sample.lunchmenu.json.*;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static anais.springboot.sample.lunchmenu.modules.json.HamcrestMatcher.match;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class LunchMenuIntegrationTest extends AbstractIntegrationTest {

    @Before
    public void stubCafeteriaService() throws Exception {
        stubFor(get(urlEqualTo("/api/cafeterias/4")).atPriority(1)
            .willReturn(okJson(GetCafeteria.json)));

        stubFor(get(urlPathMatching("/api/cafeterias/.*")).atPriority(2)
            .willReturn(notFound()));
    }

    @Test
    public void whenGetAllLunchMenus_thenReturnThreeJsonArray() throws Exception {
        when()
            .get("/lunchMenus")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(match(GetAllLunchMenuResponseSuccess.json));
    }

    @Test
    public void whenGetLunchMenu_thenReturnJsonObject() throws Exception {
        when()
            .get("/lunchMenus/1")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(match(GetLunchMenuResponseSuccess.json));
    }

    @Test
    public void whenAddLunchMenu_thenReturnLunchMenu() throws Exception {
        given()
            .contentType(ContentType.JSON)
            .body(AddLunchMenuRequestSuccess.json)
        .when()
            .post("/lunchMenus")
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .body(match(AddLunchmenuResponseSuccess.json));
    }

    @Test
    public void whenAddLunchMenuAlreadyExist_thernReturnError() throws Exception {
        given()
            .contentType(ContentType.JSON)
            .body(AddLunchMenuRequestErrorDuplicated.json)
        .when()
            .post("/lunchMenus")
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body(match(AddLunchMenuResponseErrorDuplicated.json));
    }

    @Test
    public void whenAddLunchMenuWithCafeteriaNotExist_thenReturnError() throws Exception {
        given()
            .contentType(ContentType.JSON)
            .body(AddLunchMenuRequestErrorCafeteria.json)
        .when()
            .post("/lunchMenus")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body(match(AddLunchMenuResponseErrorCafeteria.json));
    }

}
