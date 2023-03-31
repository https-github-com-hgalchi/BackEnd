package com.example.tobySpringboot;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MapRespDto {
    private final LocalDateTime timestamp=LocalDateTime.now();
    private final int status;
    private final String code;
    private final String message;
    private final String formatted_address;

    public MapRespDto(String formatted_address){
        this.status = 200;
        this.code = "Address_OK";
        this.message = "주소반환 성공";
        this.formatted_address=formatted_address;
    }
}
