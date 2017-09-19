package anais.springboot.sample.cafeteria.controller;

import anais.springboot.sample.cafeteria.model.Cafeteria;
import anais.springboot.sample.cafeteria.service.CafeteriaService;
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
@WebMvcTest(CafeteriaController.class)
public class CafeteriaControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CafeteriaService cafeteriaService;

    private static ObjectMapper mapper;

    @BeforeClass
    public static void setUp() throws Exception {
        mapper = new ObjectMapper();
    }

    @After
    public void tearDown() throws Exception {
        Mockito.reset(cafeteriaService);
    }

    @Test
    public void whenGetAllCafeterias_thenReturnJsonArray() throws Exception {
        Cafeteria cafeteria = new Cafeteria();
        cafeteria.setId(1);
        List<Cafeteria> cafeterias = Arrays.asList(cafeteria);

        given(cafeteriaService.getCafeterias()).willReturn(cafeterias);

        mvc.perform(get("/api/cafeterias")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    public void whenGetCafeteriaById_thenReturnJsonObject() throws Exception {
        Cafeteria cafe = new Cafeteria();
        cafe.setId(1);

        given(cafeteriaService.getCafeteria(1)).willReturn(Optional.of(cafe));

        mvc.perform(get("/api/cafeterias/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void whenGetCafeteriaByIdNotExist_thenReturnJsonError() throws Exception {
        Cafeteria cafe = new Cafeteria();
        cafe.setId(999);

        given(cafeteriaService.getCafeteria(999)).willReturn(Optional.empty());

        mvc.perform(get("/api/cafeterias/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode", is("ERROR.CF404")))
                .andExpect(jsonPath("$.errorMessage", is("Cannot find Cafeteria: "+999)));
    }

    @Test
    public void whenAddCafeteria_thenReturnCafeteria() throws Exception {
        Cafeteria cafeteria = new Cafeteria(1, "Napolipoli", "B2");

        given(cafeteriaService.exists(cafeteria.getId())).willReturn(false);
        given(cafeteriaService.addCafeteria(any(Cafeteria.class))).willReturn(cafeteria);

        mvc.perform(post("/api/cafeterias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cafeteria)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(cafeteria.getId())))
                .andExpect(jsonPath("$.name", is(cafeteria.getName())))
                .andExpect(jsonPath("$.location", is(cafeteria.getLocation())));
    }

    @Test
    public void whenAddCafeteriaExist_thenReturnJsonError() throws Exception {
        Cafeteria cafeteria = new Cafeteria(1, "Napolipoli", "B2");

        given(cafeteriaService.exists(cafeteria.getId())).willReturn(true);

        mvc.perform(post("/api/cafeterias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cafeteria)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is("ERROR.CF400")))
                .andExpect(jsonPath("$.errorMessage", is("Cafeteria is already exist: "+cafeteria.getId())));
    }

}
