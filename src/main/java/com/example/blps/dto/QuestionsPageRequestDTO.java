package com.example.blps.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@Getter
public class QuestionsPageRequestDTO {
    private final Pageable pageable;
}
