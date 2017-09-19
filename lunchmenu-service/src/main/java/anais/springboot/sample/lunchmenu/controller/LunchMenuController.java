package anais.springboot.sample.lunchmenu.controller;

import anais.springboot.sample.lunchmenu.exception.DataNotFoundException;
import anais.springboot.sample.lunchmenu.exception.DuplicatedException;
import anais.springboot.sample.lunchmenu.model.LunchMenu;
import anais.springboot.sample.lunchmenu.model.Cafeteria;
import anais.springboot.sample.lunchmenu.service.CafeteriaService;
import anais.springboot.sample.lunchmenu.service.LunchMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LunchMenuController {

    @Autowired
    private LunchMenuService lunchMenuService;

    @Autowired
    private CafeteriaService cafeteriaService;

    @GetMapping("/lunchMenus")
    public List<LunchMenu> getAllLunchMenus() {
        return lunchMenuService.getLunchMenus();
    }

    @GetMapping("/lunchMenus/{id}")
    public LunchMenu getLunchMenu(@PathVariable int id) {
        Optional<LunchMenu> lunchMenu = lunchMenuService.getLunchMenu(id);
        if (!lunchMenu.isPresent()) {
            throw new DataNotFoundException(LunchMenu.class, id);
        }
        return lunchMenu.get();
    }

    @PostMapping("/lunchMenus")
    @ResponseStatus(HttpStatus.CREATED)
    public LunchMenu addLunchMenu(@RequestBody LunchMenu menu) {
        if (lunchMenuService.exists(menu.getId())) {
            throw new DuplicatedException(LunchMenu.class, menu.getId());
        }
        if (!cafeteriaService.exists(menu.getCafeteriaId())) {
            throw new DataNotFoundException(Cafeteria.class, menu.getCafeteriaId());
        }
        return lunchMenuService.addLunchMenu(menu);
    }

}
