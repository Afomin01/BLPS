package com.example.blps.service.impl;

import com.example.blps.dto.ChangeUserRatingDTO;
import com.example.blps.dto.QuestionCreateDTO;
import com.example.blps.dto.QuestionRateDTO;
import com.example.blps.dto.QuestionsPageRequestDTO;
import com.example.blps.dto.TagCreateDTO;
import com.example.blps.exception.NotFoundException;
import com.example.blps.exception.GeneralValidationException;
import com.example.blps.exception.GeneralCreationException;
import com.example.blps.model.Question;
import com.example.blps.model.Tag;
import com.example.blps.model.UserQuestionVote;
import com.example.blps.repository.QuestionRepository;
import com.example.blps.repository.UserQuestionVoteRepository;
import com.example.blps.service.IQuestionService;
import com.example.blps.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class QuestionService implements IQuestionService {
    private final static int START_QUESTION_RATING = 0;
    private final static long MIN_RATING_FOR_NO_MODERATION = 512;
    private final static int MIN_RATING_FOR_NEW_TAG = 1024;
    private final static int MIN_TITLE_LENGTH = 16;
    private final static int MAX_TITLE_LENGTH = 255;
    private final static int MIN_TEXT_LENGTH = 64;
    private final static int AUTHOR_REPUTATION_ADDITION_FOR_UPVOTE = 10;
    private final static int VOTER_REPUTATION_ADDITION_FOR_VOTE = 1;

    private final QuestionRepository questionRepository;
    private final TagService tagService;
    private final IUserService userService;
    private final UserQuestionVoteRepository userQuestionVoteRepository;

    private final TransactionTemplate transactionTemplate;

    public QuestionService(final QuestionRepository questionRepository,
                           final TagService tagService,
                           final IUserService userService,
                           final PlatformTransactionManager platformTransactionManager,
                           final UserQuestionVoteRepository userQuestionVoteRepository) {
        this.questionRepository = questionRepository;
        this.tagService = tagService;
        this.userService = userService;
        transactionTemplate = new TransactionTemplate(platformTransactionManager);
        this.userQuestionVoteRepository = userQuestionVoteRepository;
    }

    @Override
    public Question createNewQuestion(QuestionCreateDTO createDTO) throws GeneralValidationException {

        int titleLength = createDTO.getTitle().length();
        int textLength = createDTO.getText().length();

        if (createDTO.getUser().getRating() < 0) {
            throw new GeneralValidationException("User rating must bu positive for asking questions");

        } else if (titleLength < MIN_TITLE_LENGTH || titleLength > MAX_TITLE_LENGTH) {
            throw new GeneralValidationException(String.format("Question title length must be in [%d, %d]",
                    MIN_TITLE_LENGTH, MAX_TITLE_LENGTH));

        } else if (textLength < MIN_TEXT_LENGTH) {
            throw new GeneralValidationException(String.format("Question text length must be greater than %d",
                    MIN_TEXT_LENGTH));

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
                .rating(START_QUESTION_RATING)
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

    @Override
    public Question rateQuestion(QuestionRateDTO dto) throws NotFoundException, GeneralValidationException {
        return transactionTemplate.execute(new TransactionCallback<Question>() {
            @Override
            public Question doInTransaction(TransactionStatus status) {
                Question question = getQuestionById(dto.getQuestionId());

                UserQuestionVote userQuestionVote = userQuestionVoteRepository.findByQuestionIdAndUserId(dto.getQuestionId(), dto.getUserId()).orElse(null);

                int newQuestionRating = question.getRating();
                if(userQuestionVote==null){
                    newQuestionRating += dto.isUpvote() ? 1 : -1;

                    question.setRating(newQuestionRating);
                    ChangeUserRatingDTO changeVoterUserRating = new ChangeUserRatingDTO(
                            dto.getUserId(),
                            VOTER_REPUTATION_ADDITION_FOR_VOTE
                    );
                    ChangeUserRatingDTO changeAuthorRating = new ChangeUserRatingDTO(
                            question.getUser().getId(),
                            dto.isUpvote() ? AUTHOR_REPUTATION_ADDITION_FOR_UPVOTE : -AUTHOR_REPUTATION_ADDITION_FOR_UPVOTE
                    );

                    userService.changeUserRating(changeVoterUserRating);
                    userService.changeUserRating(changeAuthorRating);

                }else{
                    if(userQuestionVote.isUpvote() == dto.isUpvote()){
                        return question;

                    }else {
                        newQuestionRating += dto.isUpvote() ? 2 : -2;

                        question.setRating(newQuestionRating);
                        ChangeUserRatingDTO changeUserRatingDTO = new ChangeUserRatingDTO(
                                question.getUser().getId(),
                                dto.isUpvote() ? AUTHOR_REPUTATION_ADDITION_FOR_UPVOTE *2 : -AUTHOR_REPUTATION_ADDITION_FOR_UPVOTE*2
                        );

                        userService.changeUserRating(changeUserRatingDTO);
                    }
                }

                return questionRepository.save(question);
            }
        });
    }

    @Override
    public Question getQuestionById(UUID uuid) throws NotFoundException{
        return questionRepository.
                findById(uuid).
                orElseThrow(() -> new NotFoundException("Question with UUID " + uuid + " not found."));
    }

    private boolean isQuestionNeedsModeration(long userRating) {
        return userRating < MIN_RATING_FOR_NO_MODERATION;
    }

    private Tag tryCreateNewTag(String tagName, long userRating) {
        if (userRating >= MIN_RATING_FOR_NEW_TAG) {
            return tagService.createTag(new TagCreateDTO(tagName));
        } else {
            throw new GeneralCreationException(String.format("Not enough rating to create tag '%s'", tagName));
        }
    }
}
