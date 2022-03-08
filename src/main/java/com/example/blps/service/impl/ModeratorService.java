package com.example.blps.service.impl;

import com.example.blps.exception.NotFoundException;
import com.example.blps.model.Question;
import com.example.blps.repository.QuestionRepository;
import com.example.blps.service.IModeratorService;
import com.example.blps.service.IQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

@Service
@Slf4j
public class ModeratorService implements IModeratorService {
    private final QuestionRepository questionRepository;
    private final TransactionTemplate transactionTemplate;

    public ModeratorService(final QuestionRepository questionRepository,
                            final PlatformTransactionManager platformTransactionManager) {
        this.questionRepository = questionRepository;
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    @Override
    public Question getNextUnmoderatedQuestion() throws NotFoundException {
        return questionRepository.
                getAnyUnmoderatedQuestion().
                orElseThrow(()->new NotFoundException("No unmoderated questions left."));
    }

    @Override
    public void deleteBadQuestion(UUID id) throws NotFoundException {
        if(!questionRepository.existsById(id)){
            throw  new NotFoundException("Question with UUID " + id + " not found.");
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                questionRepository.deleteById(id);
            }
        });
    }

    @Override
    public Question acceptQuestion(UUID id) throws NotFoundException {
        if(!questionRepository.existsById(id)){
            throw  new NotFoundException("Question with UUID " + id + " not found.");
        }

        Question question = questionRepository.getById(id);
        question.setNeedsModeration(false);
        question = questionRepository.save(question);

        return question;
    }
}
