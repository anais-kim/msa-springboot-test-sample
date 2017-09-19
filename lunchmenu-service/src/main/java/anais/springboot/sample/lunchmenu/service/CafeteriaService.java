package anais.springboot.sample.lunchmenu.service;

import anais.springboot.sample.lunchmenu.exception.CafeteriaServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CafeteriaService {

    @Value("${api.gateway}")
    private String cafeteriaEndpoint;

    @Autowired
    RestTemplate restClient;

    public boolean exists(int cafeteriaId) {
        String url = new StringBuilder(cafeteriaEndpoint).append("/api/cafeterias/").append(cafeteriaId).toString();
        try {
            restClient.getForEntity(url, Map.class);
        } catch(HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) return false;
            else throw new CafeteriaServiceException();
        }
        return true;
    }
}
