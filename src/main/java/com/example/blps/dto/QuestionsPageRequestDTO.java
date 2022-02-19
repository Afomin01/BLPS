package com.example.blps.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.print.Pageable;

@AllArgsConstructor
@Getter
public class QuestionsPageRequestDTO {
    private final Pageable pageable;
}
