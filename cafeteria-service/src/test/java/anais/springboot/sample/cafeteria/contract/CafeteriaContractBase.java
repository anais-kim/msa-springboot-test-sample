package anais.springboot.sample.cafeteria.contract;

import anais.springboot.sample.cafeteria.controller.CafeteriaController;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;

public abstract class CafeteriaContractBase {

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new CafeteriaController());
    }

}
