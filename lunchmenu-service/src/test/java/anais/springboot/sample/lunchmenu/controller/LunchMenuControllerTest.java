package anais.springboot.sample.lunchmenu.controller;

import anais.springboot.sample.lunchmenu.model.LunchMenu;
import anais.springboot.sample.lunchmenu.service.CafeteriaService;
import anais.springboot.sample.lunchmenu.service.LunchMenuService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LunchMenuController.class)
public class LunchMenuControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    LunchMenuService lunchMenuService;

    @MockBean
    CafeteriaService cafeteriaService;

    private static ObjectMapper mapper;

    @BeforeClass
    public static void setUp() throws Exception {
        mapper = new ObjectMapper();
    }

    @After
    public void tearDown() throws Exception {
        Mockito.reset(lunchMenuService);
    }

    @Test
    public void whenGetAllLunchMenus_thenReturnJsonArray() throws Exception {
        LunchMenu lunchMenu = new LunchMenu(1, "Pizza", 1, 1000);
        lunchMenu.setId(1);
        List<LunchMenu> lunchMenus = Arrays.asList(lunchMenu);

        given(lunchMenuService.getLunchMenus()).willReturn(lunchMenus);

        mvc.perform(get("/api/lunchMenus")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    public void whenGetLunchById_thenReturnJsonObject() throws Exception {
        LunchMenu lunchMenu = new LunchMenu(1, "Pizza", 1, 1000);
        lunchMenu.setId(1);

        given(lunchMenuService.getLunchMenu(1)).willReturn(Optional.of(lunchMenu));

        mvc.perform(get("/api/lunchMenus/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void whenGetLunchByIdNotExist_thenReturnJsonError() throws Exception {
        LunchMenu lunchMenu = new LunchMenu(1, "Pizza", 1, 1000);
        lunchMenu.setId(999);

        given(lunchMenuService.getLunchMenu(999)).willReturn(Optional.empty());

        mvc.perform(get("/api/lunchMenus/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode", is("ERROR.LM404")))
                .andExpect(jsonPath("$.errorMessage", is("Cannot find LunchMenu: "+999)));
    }

    @Test
    public void whenAddLunchMenu_thenReturnLunchMenu() throws Exception {
        LunchMenu lunchMenu = new LunchMenu(1, "Pizza", 1, 1000);

        given(lunchMenuService.exists(lunchMenu.getId())).willReturn(false);
        given(cafeteriaService.exists(lunchMenu.getCafeteriaId())).willReturn(true);
        given(lunchMenuService.addLunchMenu(any(LunchMenu.class))).willReturn(lunchMenu);

        mvc.perform(post("/api/lunchMenus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lunchMenu)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(lunchMenu.getId())))
                .andExpect(jsonPath("$.name", is(lunchMenu.getName())))
                .andExpect(jsonPath("$.cafeteriaId", is(lunchMenu.getCafeteriaId())))
                .andExpect(jsonPath("$.calorie", is(lunchMenu.getCalorie())));
    }

    @Test
    public void whenAddLunchMenuExist_thenReturnJsonError() throws Exception {
        LunchMenu lunchMenu = new LunchMenu(1, "Pizza", 1, 1000);

        given(lunchMenuService.exists(lunchMenu.getId())).willReturn(true);

        mvc.perform(post("/api/lunchMenus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lunchMenu)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is("ERROR.LM400")))
                .andExpect(jsonPath("$.errorMessage", is("LunchMenu is already exist: "+lunchMenu.getId())));
    }

    @Test
    public void whenAddLunchMenuWtihCafeteriaNotExist_thenReturnJsonError() throws Exception {
        LunchMenu lunchMenu = new LunchMenu(1, "Pizza", 1, 1000);

        given(lunchMenuService.exists(lunchMenu.getId())).willReturn(false);
        given(cafeteriaService.exists(lunchMenu.getCafeteriaId())).willReturn(false);

        mvc.perform(post("/api/lunchMenus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lunchMenu)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode", is("ERROR.LM404")))
                .andExpect(jsonPath("$.errorMessage", is("Cannot find Cafeteria: "+lunchMenu.getCafeteriaId())));
    }
}
