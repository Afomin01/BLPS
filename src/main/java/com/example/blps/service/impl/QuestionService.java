package com.example.blps.service.impl;

import com.example.blps.dto.QuestionCreateDTO;
import com.example.blps.dto.QuestionsPageRequestDTO;
import com.example.blps.dto.TagCreateDTO;
import com.example.blps.exception.QuestionValidationException;
import com.example.blps.exception.TagCreationException;
import com.example.blps.model.Question;
import com.example.blps.model.Tag;
import com.example.blps.repository.QuestionRepository;
import com.example.blps.service.IQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class QuestionService implements IQuestionService {
    private final static int START_QUESTION_VOTES = 0;
    private final static long MIN_RATING_FOR_NO_MODERATION = 1001;
    private final static int MIN_RATING_FOR_NEW_TAG = 1001;
    private final static int MIN_TITLE_LENGTH = 10;
    private final static int MAX_TITLE_LENGTH = 255;
    private final static int MIN_TEXT_LENGTH = 20;
    private final static int MAX_TEXT_LENGTH = 255;

    private final QuestionRepository questionRepository;
    private final TagService tagService;

    public QuestionService(final QuestionRepository questionRepository, final TagService tagService) {
        this.questionRepository = questionRepository;
        this.tagService = tagService;
    }

    @Override
    public Question createNewQuestion(QuestionCreateDTO createDTO) {

        int titleLength = createDTO.getTitle().length();
        int textLength = createDTO.getText().length();
        if (createDTO.getUser().getRating() < 0) {
            throw new QuestionValidationException("User rating must bu positive for asking questions");
        } else if (titleLength < MIN_TITLE_LENGTH || titleLength > MAX_TITLE_LENGTH) {
            throw new QuestionValidationException(String.format("Question title length must be in [%d, %d]",
                    MIN_TITLE_LENGTH, MAX_TITLE_LENGTH));
        } else if (textLength < MIN_TEXT_LENGTH || textLength > MAX_TEXT_LENGTH) {
            throw new QuestionValidationException(String.format("Question text length must be in [%d, %d]",
                    MIN_TEXT_LENGTH, MAX_TEXT_LENGTH));
        }

        Set<Tag> tags = new HashSet<>();
        for (String tagName : createDTO.getTags()) {
            Tag tag = tagService.getTagByName(tagName).orElseGet(
                    () -> tryCreateNewTag(tagName, createDTO.getUser().getRating()));
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

    private Tag tryCreateNewTag(String tagName, long userRating) {
        if (userRating >= MIN_RATING_FOR_NEW_TAG) {
            return tagService.createTag(new TagCreateDTO(tagName));
        } else {
            throw new TagCreationException(String.format("Not enough rating to create tag '%s'", tagName));
        }
    }
}
