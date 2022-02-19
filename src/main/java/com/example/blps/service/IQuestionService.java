package com.example.blps.service;

import com.example.blps.dto.QuestionCreateDTO;
import com.example.blps.dto.QuestionsPageRequestDTO;
import com.example.blps.model.Question;
import org.springframework.data.domain.Page;

public interface IQuestionService {
    Question createNewQuestion(QuestionCreateDTO createDTO);

    Page<Question> getPageOfQuestion(QuestionsPageRequestDTO pageRequestDTO);
}
