package anais.springboot.sample.cafeteria.service;

import anais.springboot.sample.cafeteria.model.Cafeteria;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CafeteriaService {

    private List<Cafeteria> cafeterias;

    @PostConstruct
    public void init() {
        cafeterias = new ArrayList<>();
        Cafeteria cafeteria1 = new Cafeteria(1, "Napolipoli", "B1");
        Cafeteria cafeteria2 = new Cafeteria(2, "XingPu", "B1");
        Cafeteria cafeteria3 = new Cafeteria(3, "AsianPics", "B2");
        Cafeteria cafeteria4 = new Cafeteria(4, "SnapSnack", "B2");
        cafeterias.addAll(Arrays.asList(cafeteria1, cafeteria2, cafeteria3, cafeteria4));
    }

    public List<Cafeteria> getCafeterias() {
        return cafeterias;
    }

    public Optional<Cafeteria> getCafeteria(int id) {
        return cafeterias.stream()
                .filter(l -> l.getId() == id)
                .findFirst();
    }

    public Cafeteria addCafeteria(Cafeteria cafeteria) {
        cafeterias.add(cafeteria);
        return cafeteria;
    }

    public boolean exists(int id) {
        return getCafeteria(id).isPresent();
    }

}
