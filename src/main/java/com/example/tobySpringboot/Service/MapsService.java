package com.example.tobySpringboot.Service;

import com.example.tobySpringboot.Enum.Constant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.*;

@Service
public class MapsService {

    @Value("${google.client.key}")
    private String key;

    @Value("${data.client.key}")
    private String serviceKey;
    private String language = "ko";

    private final RestTemplate restTemplate;

    private final Distance distance;
    public MapsService(Distance distance){
        this.distance = distance;
        this.restTemplate=new RestTemplate();
    }
    public ArrayList<Map<String,JsonNode>>  geolocation(Map<String,String>data){

        String latlng = data.get("lat") + "," + data.get("lng");

        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+latlng
                +"&language="+language
                + "&key=" + key;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        Map<String, JsonNode> map = new HashMap<>();
        ArrayList<Map<String,JsonNode>> result = new ArrayList<>();


        //복합적인 json형태는 map으로 매핑할 수 없음.  json을 Object로 바꿔주는 jackson를 라이브러리를사용
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                JsonNode.class);

        JsonNode temp=response.getBody();

        map.put("formatted_address", temp.path("results")
                .get(0).path("formatted_address"));

        result.add(map);

        return result;

    }


    //가까운 응급실 반환
    public ArrayList<Map<String,Object>> getLocationNear(Constant findtype,Map<String,String> data)
            throws URISyntaxException, ParseException, JsonProcessingException {

        String endpoint = null;
        String url;
        String addr=null;

        //위도,경도를 주소로 변환
        String[] addrlist = geolocation(data).get(0).get("formatted_address").toString().split(" ");


        switch (findtype) {
            case HOSPITAL:{
                endpoint = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire";
                addr = addrlist[2] + addrlist[3]+addrlist[4].substring(0, (addrlist[4].length() - 1));
            }break;
            case EMERGENCY:{
                endpoint = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytListInfoInqire";
                addr = addrlist[2];
            }break;
            case AED:{
                endpoint = "http://apis.data.go.kr/B552657/AEDInfoInqireService/getEgytAedManageInfoInqire";
            }break;
            default:{
                throw new IllegalArgumentException("알 수 없는 요청형식입니다.");
            }
        };

        System.out.println(addr);

        url=endpoint+ "?serviceKey="+serviceKey+
                "&Q0="+URLEncoder.encode(addr)+
                "&ORD=ADDR"+
                "&pageNo=1" +
                "&numOfRows=100";



        URI uri = new URI(url.toString());
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                String.class);


        JsonNode temp = new ObjectMapper().readTree(response.getBody());

        ArrayList<Map<String,Object>> result = new ArrayList<>();

        ObjectNode updatedJsonNode =  JsonNodeFactory.instance.objectNode();

        temp.path("response")
                .path("body").path("items").path("item")
                        .forEach(i-> {
                                Map<String, Object> map = new LinkedHashMap<>();
                                map.put("dutyName",i.path("dutyName"));
                                map.put("dutyAddr",i.path("dutyAddr"));
                                map.put("dutyDivName", i.path("dutyDivNam"));
                                map.put("dutyTell", i.path("dutyTel1"));
                                map.put("dutyTime", i.path("dutyTime1c"+i.path("dutyTime1s")));
                                map.put("wgs84Lat",i.path("wgs84Lat"));
                                map.put("wgs84Lon",i.path("wgs84Lon"));


                                //dutytime 묶기
                                Iterator<String> names=i.fieldNames();
                                while (names.hasNext()) {
                                    String str=names.next();
                                    if (str.startsWith("dutyTime")) {
                                        updatedJsonNode.put(str, i.path(str));
                                    }
                                }
                                map.put("dutyTime", updatedJsonNode);

                                // 수정사항 dutytime이 빈 객체일 때 거르기
                                Iterator<String> iter=map.keySet().iterator();
                                while (iter.hasNext()) {
                                    String str= iter.next();
                                    if (map.get(str).getClass() == MissingNode.class) {
                                        iter.remove();
                                    }
                                }

                            map.put("distance",distance.getdistance(data, i.path("wgs84Lat").asDouble(), i.path("wgs84Lon").asDouble()));

                                result.add(map);

                        });

        //result의 거리 key값의 value를 오름차순으로 정렬

        Collections.sort(result, Comparator.comparing(m ->Double.parseDouble( m.get("distance").toString())));
      /*  for(Map<String,Object> map:result)
            System.out.println(map);*/



        return result;
    }


}
