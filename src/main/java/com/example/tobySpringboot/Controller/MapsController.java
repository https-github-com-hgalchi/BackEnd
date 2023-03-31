package com.example.tobySpringboot.Google;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/map")
public class MapsController {

    @Value("${google.client.key}")
    private String key;
    private String latlng = "37.56648173732714,126.97817695835042";
    private String language = "ko";

    private final RestTemplate restTemplate;

    public MapsController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    //현재
    /*@GetMapping("/location")
    public JsonNode geolocation() {
        String url = "https://www.googleapis.com/geolocation/v1/geolocate"
                + "?key=" + key;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                JsonNode.class);


        return response.getBody();
    }
*/
    //위도와 경도를 주소로 반환합니다.
    @GetMapping("/location")
    public Map<String,String> geolocation(@RequestParam(value = "lat", required = false) String lat, @RequestParam(value = "lng", required = false) String lng) {

        String latlling=lat+","+lng;
        Map map = new HashMap();
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+latlling
                +"&language="+language
                + "&key=" + key;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                JsonNode.class);

        JsonNode temp=response.getBody();
        String formatted_address = temp.path("results").get(0).path("formatted_address").toString();
        map.put("formatted_address", formatted_address);
        return map;



    }


}
