package com.example.tobySpringboot.Dto;

import com.example.tobySpringboot.Enum.Status;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Stack;

@Getter
public class MapRespDto {
    private final LocalDateTime timestamp=LocalDateTime.now();
    private final HttpStatus status;
    private final String code;
    private final String message;
    private final String formatted_address;

    public MapRespDto(String formatted_address, Status status){
        this.status = status.getStatus();
        this.code =status.name();
        this.message = status.getMessage();
        this.formatted_address=formatted_address;
    }
}
