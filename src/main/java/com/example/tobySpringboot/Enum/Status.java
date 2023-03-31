package com.example.tobySpringboot.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Status {
    Location_OK(HttpStatus.OK, "주소변환 성공");

    private final HttpStatus status;
    private final String message;
}
