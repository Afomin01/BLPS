package com.example.blps.dto.response;

import com.example.blps.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class QuestionContentResponse {
    private final UUID id;
    private final String title;
    private final String text;
    private final List<TagInfoResponse> tags;
    private final Instant creationDateUTC;
    private final int rating;
    private final boolean needsModeration;

    public QuestionContentResponse(Question question) {
        id = question.getId();
        title = question.getTitle();
        text = question.getText();
        tags = question.getTags().stream().map(TagInfoResponse::new).collect(Collectors.toList());
        creationDateUTC = question.getCreationTimeUTC();
        rating = question.getVotes();
        needsModeration = question.isNeedsModeration();
    }
}
