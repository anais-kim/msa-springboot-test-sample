package anais.springboot.sample.cafeteria.controller;

import anais.springboot.sample.cafeteria.exception.DataNotFoundException;
import anais.springboot.sample.cafeteria.exception.DuplicatedException;
import anais.springboot.sample.cafeteria.model.Cafeteria;
import anais.springboot.sample.cafeteria.service.CafeteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CafeteriaController {

    @Autowired
    private CafeteriaService cafeteriaService;

    @GetMapping("/cafeterias")
    public List<Cafeteria> getAllCafeterias() {
        return cafeteriaService.getCafeterias();
    }

    @GetMapping("/cafeterias/{id}")
    public Cafeteria getCafeteria(@PathVariable int id) {
        Optional<Cafeteria> cafeteria = cafeteriaService.getCafeteria(id);
        if (!cafeteria.isPresent()) {
            throw new DataNotFoundException(Cafeteria.class, id);
        }
        return cafeteria.get();
    }

    @PostMapping("/cafeterias")
    @ResponseStatus(HttpStatus.CREATED)
    public Cafeteria addCafeteria(@RequestBody Cafeteria cafeteria) {
        if (cafeteriaService.exists(cafeteria.getId())) {
            throw new DuplicatedException(Cafeteria.class, cafeteria.getId());
        }
        return cafeteriaService.addCafeteria(cafeteria);
    }

}
