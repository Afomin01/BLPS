package com.example.blps.service;

import com.example.blps.dto.QuestionCreateDTO;
import com.example.blps.dto.QuestionRateDTO;
import com.example.blps.dto.QuestionsPageRequestDTO;
import com.example.blps.exception.NotFoundException;
import com.example.blps.exception.GeneralValidationException;
import com.example.blps.model.Question;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IQuestionService {
    Question createNewQuestion(QuestionCreateDTO createDTO) throws GeneralValidationException;

    Page<Question> getPageOfQuestion(QuestionsPageRequestDTO pageRequestDTO);

    Question rateQuestion(QuestionRateDTO dto) throws NotFoundException, GeneralValidationException;

    Question getQuestionById(UUID uuid) throws NotFoundException;
}
