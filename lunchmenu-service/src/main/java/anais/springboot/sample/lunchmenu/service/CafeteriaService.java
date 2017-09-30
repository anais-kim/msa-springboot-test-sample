package anais.springboot.sample.lunchmenu.service;

import anais.springboot.sample.lunchmenu.exception.CafeteriaServiceException;
import anais.springboot.sample.lunchmenu.model.Cafeteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CafeteriaService {

    @Value("${api.gateway}")
    private String cafeteriaEndpoint;

    @Autowired
    RestTemplate restClient;

    public boolean exists(int cafeteriaId) {
        String url = new StringBuilder(cafeteriaEndpoint).append("/api/cafeterias/").append(cafeteriaId).toString();
        try {
            ResponseEntity<Cafeteria> response = restClient.getForEntity(url, Cafeteria.class);
            if (response.getStatusCode() != HttpStatus.OK) return false;
            else if (response.getBody() == null || response.getBody().getId() != cafeteriaId) {
                throw new CafeteriaServiceException();
            }
        } catch(HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) return false;
            else throw new CafeteriaServiceException();
        }
        return true;
    }
}
