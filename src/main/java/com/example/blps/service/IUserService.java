package com.example.blps.service;

import com.example.blps.dto.UserCreateDTO;
import com.example.blps.model.User;

public interface IUserService {
    User createNewUser(UserCreateDTO createDTO);
}