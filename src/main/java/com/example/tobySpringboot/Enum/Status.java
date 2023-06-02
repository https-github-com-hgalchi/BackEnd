package com.example.tobySpringboot.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Status {
    Location_OK(HttpStatus.OK, "주소변환 성공"),
    EMERGENCY_OK(HttpStatus.OK, "주변응급실 찾기 성공"),
    HOSPITAL_OK(HttpStatus.OK, "주변병원 찾기 성공"),
    AED_OK(HttpStatus.OK, "주변ADE 찾기 성공"),

    Question_OK(HttpStatus.OK,"질문답변받기 성공");

    private final HttpStatus status;
    private final String message;
}
