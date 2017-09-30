package anais.springboot.sample.lunchmenu;

import anais.springboot.sample.lunchmenu.json.*;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static anais.springboot.sample.lunchmenu.support.matcher.HamcrestMatcher.match;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class LunchMenuIntegrationTest extends AbstractIntegrationTest {

    @Before
    public void stubCafeteriaService() throws Exception {
        stubFor(get(urlEqualTo("/api/cafeterias/4")).atPriority(1)
            .willReturn(okJson(GetCafeteriaSuccessResponse.json)));

        stubFor(get(urlPathMatching("/api/cafeterias/.*")).atPriority(2)
            .willReturn(notFound()));
    }

    @Test
    public void whenGetAllLunchMenus_thenReturnThreeJsonArray() throws Exception {
        when()
            .get("/lunchMenus")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(match(GetAllLunchMenuSuccessResponse.json));
    }

    @Test
    public void whenGetLunchMenu_thenReturnJsonObject() throws Exception {
        when()
            .get("/lunchMenus/1")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(match(GetLunchMenuSuccessResponse.json));
    }

    @Test
    public void whenAddLunchMenu_thenReturnLunchMenu() throws Exception {
        given()
            .body(AddLunchMenuSuccessRequest.json)
        .when()
            .post("/lunchMenus")
        .then()
            .statusCode(HttpStatus.SC_CREATED)
            .body(match(AddLunchmenuSuccessResponse.json));
    }

    @Test
    public void whenAddLunchMenuAlreadyExist_thernReturnError() throws Exception {
        given()
            .body(AddLunchMenuErrorDuplicatedRequest.json)
        .when()
            .post("/lunchMenus")
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body(match(AddLunchMenuErrorDuplicatedResponse.json));
    }

    @Test
    public void whenAddLunchMenuWithCafeteriaNotExist_thenReturnError() throws Exception {
        given()
            .body(AddLunchMenuErrorCafeteriaRequest.json)
        .when()
            .post("/lunchMenus")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body(match(AddLunchMenuErrorCafeteriaResponse.json));
    }

}
