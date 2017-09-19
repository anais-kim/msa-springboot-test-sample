package anais.springboot.sample.lunchmenu.service;

import anais.springboot.sample.lunchmenu.model.LunchMenu;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class LunchMenuService {

    private List<LunchMenu> lunchMenus;

    @PostConstruct
    public void init() {
        lunchMenus = new ArrayList<>();
        LunchMenu menu1 = new LunchMenu(1, "Pizza", 1, 1000);
        LunchMenu menu2 = new LunchMenu(2, "Champon", 2, 900);
        LunchMenu menu3 = new LunchMenu(3, "Pho", 3, 700);
        lunchMenus.addAll(Arrays.asList(menu1, menu2, menu3));
    }

    public List<LunchMenu> getLunchMenus() {
        return lunchMenus;
    }

    public Optional<LunchMenu> getLunchMenu(int id) {
        return lunchMenus.stream()
                .filter(l -> l.getId() == id)
                .findFirst();
    }

    public LunchMenu addLunchMenu(LunchMenu menu) {
        lunchMenus.add(menu);
        return menu;
    }

    public boolean exists(int id) {
        return getLunchMenu(id).isPresent();
    }

}
