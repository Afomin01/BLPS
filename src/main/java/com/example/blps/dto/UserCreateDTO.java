package com.example.blps.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserCreateDTO {
    private final String username;
    private final String password;
}
