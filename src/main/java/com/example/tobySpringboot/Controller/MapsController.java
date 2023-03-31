package com.example.tobySpringboot.Controller;

import com.example.tobySpringboot.Dto.MapRespDto;
import com.example.tobySpringboot.Service.MapsService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.example.tobySpringboot.Enum.Status.Location_OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map")
public class MapsController {

    private final MapsService mapsService;


    //현재 위치를 위도와 경도로 반환합니다.
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
    public ResponseEntity<MapRespDto> geolocation(@RequestParam Map<String,String>data) {

        String latlng=data.get("lat")+","+data.get("lng");
        String formatted_address= mapsService.geolocation(latlng);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MapRespDto(formatted_address,Location_OK));

    }

}
