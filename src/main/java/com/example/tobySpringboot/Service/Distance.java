package com.example.tobySpringboot.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Distance {


    public double getdistance(Map<String,String> data, double lat2, double lon2) {
            double theta, dist;
        Double lat = Double.parseDouble(data.get("lat"));
        Double lng = Double.parseDouble(data.get("lng"));

            theta = lng - lon2;
            dist = Math.sin(deg2rad(lat)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            dist=dist*1.609344;
            /*switch(unit) {
                case 'M':
                    break;
                case 'K':
                    dist = dist * 1.609344;
                    break;
                case 'N':
                    dist = dist * 0.8684;
                    break;
            }*/
            //System.out.println("두 좌표간 거리는 "+dist);
            return (dist);
        }


    double deg2rad(double deg) {
        return (deg * Math.PI / 180);
    }

    double rad2deg(double rad) {
        return (rad * 180 /  Math.PI);
    }
}
