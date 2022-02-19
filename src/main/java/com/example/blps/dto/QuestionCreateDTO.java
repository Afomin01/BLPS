package com.example.blps.dto;

import com.example.blps.model.Tag;
import com.example.blps.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class QuestionCreateDTO {
    private final String title;
    private final String text;
    private final List<Tag> tags;
    private final User user;
}
