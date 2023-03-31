package com.example.tobySpringboot.Service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class MapsService {

    @Value("${google.client.key}")
    private String key;
    private String language = "ko";

    private final RestTemplate restTemplate;

    public MapsService(){
        this.restTemplate=new RestTemplate();
    }
    public String geolocation(String latlng){
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+latlng
                +"&language="+language
                + "&key=" + key;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        //복합적인 json형태는 map으로 매핑할 수 없음.  json을 Object로 바꿔주는 jackson를 라이브러리를사용
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                JsonNode.class);

        JsonNode temp=response.getBody();

        return temp.path("results")
                .get(0).path("formatted_address")
                .toString();
    }
}
