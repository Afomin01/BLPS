package com.example.blps.controller;

import com.example.blps.dto.response.QuestionContentResponse;
import com.example.blps.model.Question;
import com.example.blps.service.IModeratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/moderate")
public class ModeratorController {
    private final IModeratorService moderatorService;

    public ModeratorController(final IModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }

    @GetMapping(value = "/question")
    public ResponseEntity<QuestionContentResponse> getUnmoderatedQuestion(){
        Question question = moderatorService.getNextUnmoderatedQuestion();

        QuestionContentResponse response = new QuestionContentResponse(question);

        return ResponseEntity.
                ok().
                contentType(MediaType.APPLICATION_JSON).
                body(response);
    }

    @DeleteMapping(value = "/question/{id}")
    public ResponseEntity<?> deleteQuestionById(@PathVariable final UUID id){
        moderatorService.deleteBadQuestion(id);

        return ResponseEntity.
                noContent().
                build();
    }

    @PatchMapping(value = "/question/{id}")
    public ResponseEntity<QuestionContentResponse> acceptQuestion(@PathVariable final UUID id){
        Question question = moderatorService.acceptQuestion(id);

        QuestionContentResponse response = new QuestionContentResponse(question);

        return ResponseEntity.
                ok().
                contentType(MediaType.APPLICATION_JSON).
                body(response);
    }


}
