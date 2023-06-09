package com.example.tobySpringboot.Controller;

import com.example.tobySpringboot.Dto.MapRespDto;
import com.example.tobySpringboot.Enum.Constant;
import com.example.tobySpringboot.Enum.Status;
import com.example.tobySpringboot.Service.MapsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.*;

import static com.example.tobySpringboot.Enum.Status.EMERGENCY_OK;
import static com.example.tobySpringboot.Enum.Status.Location_OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map")
public class MapsController {

    private final MapsService mapsService;

    //위도와 경도를 주소로 반환합니다.
    @GetMapping("/getLocation")
    public ResponseEntity<MapRespDto> getLocation(@RequestParam Map<String, String> data) {

        ArrayList<Map<String,JsonNode>> body = mapsService.geolocation(data);
        //System.out.println(body);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MapRespDto.builder()
                        .body(body)
                        .status(Location_OK)
                        .build());

    }

    //주변 응급실,병원,AED위치 제공
    @GetMapping("/getLocationNear/{findtype}")
    public ResponseEntity<MapRespDto> getLocationNear(@PathVariable(name="findtype") String findtype, @RequestParam Map<String,String>data) throws UnsupportedEncodingException, URISyntaxException, ParseException, JsonProcessingException {

        Double lat = Double.parseDouble(data.get("lat"));
        Double lng = Double.parseDouble(data.get("lng"));
        Constant type = Constant.valueOf(findtype);

        ArrayList<Map<String, Object>> body1 = mapsService.getLocationNear(type,data);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MapRespDto.builder()
                        .body1(body1)
                        .status(Status.valueOf(findtype+"_OK"))
                        .build());
    }



}
