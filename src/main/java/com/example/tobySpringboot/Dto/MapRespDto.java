package com.example.tobySpringboot.Dto;

import com.example.tobySpringboot.Enum.Status;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class MapRespDto {
    private final LocalDateTime timestamp=LocalDateTime.now();
    private final HttpStatus status;
    private final String code;
    private final String message;

    private final ArrayList<Map<String,JsonNode>> body;

    private final List<Map<String,Object>> body1;

    @Builder
    public MapRespDto(Status status, ArrayList<Map<String,JsonNode>> body, List<Map<String,Object>> body1){
        this.status = status.getStatus();
        this.code =status.name();
        this.message = status.getMessage();
        this.body = body;
        this.body1=body1;

    }

}
