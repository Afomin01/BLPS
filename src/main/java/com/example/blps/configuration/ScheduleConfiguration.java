package com.example.blps.configuration;

import com.example.blps.model.Question;
import com.example.blps.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
public class ScheduleConfiguration {

    private final QuestionRepository questionRepository;
    private final static int QUESTION_RATING_TO_DELETE = -25;
    private final static int QUESTION_RATING_FOR_NEW_MODERATION = -10;

    public ScheduleConfiguration(final QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void manageBadQuestions() {
        log.info("Started managing bad questions.");

        List<Question> awfulQuestions = questionRepository.findQuestionsByRatingLessThan(QUESTION_RATING_TO_DELETE);

        awfulQuestions.stream().parallel().forEach(question -> {
            questionRepository.delete(question);
            log.info("Question with id " + question.getId() + " with rating " + question.getRating() + " was deleted because it has too low rating.");
        });


        List<Question> badQuestions = questionRepository.findUnmoderatedQuestionsByRatingLessThan(QUESTION_RATING_FOR_NEW_MODERATION);

        badQuestions.stream().parallel().forEach(question -> {
            question.setNeedsModeration(true);
            log.info("Question with id " + question.getId() + " with rating " + question.getRating() + " was sent back to moderation.");
        });

        log.info("Finished managing bad questions.");
    }
}
