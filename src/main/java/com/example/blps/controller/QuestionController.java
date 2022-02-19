package com.example.blps.controller;

import com.example.blps.dto.QuestionCreateDTO;
import com.example.blps.dto.QuestionsPageRequestDTO;
import com.example.blps.dto.request.QuestionCreateRequest;
import com.example.blps.dto.response.QuestionContentResponse;
import com.example.blps.model.Question;
import com.example.blps.model.User;
import com.example.blps.service.IQuestionService;
import com.example.blps.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class QuestionController {
    private final IQuestionService questionService;
    private final IUserService userService;

    public QuestionController(final IQuestionService questionService,
                              final IUserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<QuestionContentResponse> createNewQuestion(@RequestBody final QuestionCreateRequest request,
                                                                     final Principal principal) {

        User user = userService.loadUserByUsername(principal.getName());

        QuestionCreateDTO createDTO = new QuestionCreateDTO(
                request.getTitle(),
                request.getText(),
                request.getTagsNames(),
                user
        );

        Question question = questionService.createNewQuestion(createDTO);

        QuestionContentResponse response = new QuestionContentResponse(question);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }


    @GetMapping
    ResponseEntity<List<QuestionContentResponse>> getPageOfQuestions(@ParameterObject Pageable pageable) {
        QuestionsPageRequestDTO questionsPageRequestDTO = new QuestionsPageRequestDTO(pageable);

        Page<Question> page = questionService.getPageOfQuestion(questionsPageRequestDTO);

        List<QuestionContentResponse> response = page.
                getContent().
                stream().
                map(QuestionContentResponse::new).
                collect(Collectors.toList());

        return ResponseEntity.
                ok().
                contentType(MediaType.APPLICATION_JSON).
                body(response);
    }


}
