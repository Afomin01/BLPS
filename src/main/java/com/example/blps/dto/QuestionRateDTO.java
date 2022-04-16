package com.example.blps.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class QuestionRateDTO {
    private final UUID questionId;
    private final UUID userId;
    private final boolean isUpvote;
}
