package com.example.tobySpringboot.ChatGpt;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class QuestionRequest {

    private List<Map<String, String>> messages=new ArrayList<>();
    private String question;

    public QuestionRequest(String question) {
        this.question = question;

        Map<String, String> map2 = new HashMap<>();
        map2.put("role", "user");
        map2.put("content", question);
        messages.add(map2);

    }





}
