package com.example.tobySpringboot.Controller;

import com.example.tobySpringboot.ChatGpt.ChatGptService;
import com.example.tobySpringboot.ChatGpt.QuestionRequest;
import com.example.tobySpringboot.ChatGpt.QuestionRequestDto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ask")
public class AskController {
    private final ChatGptService chatGptService;

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String getAnswer(@RequestBody String question) {
        QuestionRequest questionRequest = new QuestionRequest(question);
        return chatGptService.askQuestion(questionRequest);
    }
}
