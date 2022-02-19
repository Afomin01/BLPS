package com.example.blps.service.impl;

import com.example.blps.dto.QuestionCreateDTO;
import com.example.blps.model.Question;
import com.example.blps.repository.QuestionRepository;
import com.example.blps.service.IQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuestionService implements IQuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(final QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question createNewQuestion(QuestionCreateDTO createDTO) {
        return null;
    }
}
