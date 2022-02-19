package com.example.blps.service.impl;

import com.example.blps.dto.QuestionCreateDTO;
import com.example.blps.dto.QuestionsPageRequestDTO;
import com.example.blps.exception.QuestionValidationException;
import com.example.blps.model.Question;
import com.example.blps.model.Tag;
import com.example.blps.repository.QuestionRepository;
import com.example.blps.repository.TagRepository;
import com.example.blps.service.IQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class QuestionService implements IQuestionService {
    private final static int START_QUESTION_VOTES = 0;
    private final static long MIN_RATING_FOR_NO_MODERATION = 1001;
    private final static int MIN_TITLE_LENGTH = 10;
    private final static int MIN_TEXT_LENGTH = 20;

    private final QuestionRepository questionRepository;
    private final TagRepository tagRepository;

    public QuestionService(final QuestionRepository questionRepository, final TagRepository tagRepository) {
        this.questionRepository = questionRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Question createNewQuestion(QuestionCreateDTO createDTO) {

        if (createDTO.getUser().getRating() < 0) {
            throw new QuestionValidationException("User rating must bu positive for asking questions");
        } else if (createDTO.getTitle().length() < MIN_TITLE_LENGTH) {
            throw new QuestionValidationException(String.format("Question title length must be greater then %d", MIN_TITLE_LENGTH));
        } else if (createDTO.getText().length() < MIN_TEXT_LENGTH) {
            throw new QuestionValidationException(String.format("Question text length must be greater then %d", MIN_TEXT_LENGTH));
        }

        Set<Tag> tags = new HashSet<>();
        for (String tagName : createDTO.getTags()) {
            Tag tag = tagRepository.findByName(tagName)
                    .orElseThrow(() -> new QuestionValidationException(String.format("Invalid tag name '%s'", tagName)));
            tags.add(tag);
        }

        Question question = Question.builder()
                .title(createDTO.getTitle())
                .text(createDTO.getText())
                .votes(START_QUESTION_VOTES)
                .user(createDTO.getUser())
                .tags(tags)
                .needsModeration(isQuestionNeedsModeration(createDTO.getUser().getRating()))
                .build();
        question = questionRepository.save(question);

        return question;
    }

    @Override
    public Page<Question> getPageOfQuestion(QuestionsPageRequestDTO pageRequestDTO) {
        return questionRepository.findAll(pageRequestDTO.getPageable());
    }

    private boolean isQuestionNeedsModeration(long userRating) {
        return userRating >= MIN_RATING_FOR_NO_MODERATION;
    }
}
