package com.example.blps.service;

import com.example.blps.dto.QuestionCreateDTO;
import com.example.blps.model.Question;

public interface IQuestionService {
    Question createNewQuestion(QuestionCreateDTO createDTO);
}
