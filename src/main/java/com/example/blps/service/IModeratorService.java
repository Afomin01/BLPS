package com.example.blps.service;

import com.example.blps.exception.GeneralValidationException;
import com.example.blps.exception.NotFoundException;
import com.example.blps.model.Question;

import java.util.UUID;

public interface IModeratorService {
    Question getNextUnmoderatedQuestion() throws NotFoundException;

    void deleteBadQuestion(UUID id) throws NotFoundException;

    Question acceptQuestion(UUID id) throws NotFoundException, GeneralValidationException;
}
