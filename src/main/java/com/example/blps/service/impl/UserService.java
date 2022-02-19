package com.example.blps.service.impl;

import com.example.blps.dto.UserCreateDTO;
import com.example.blps.model.User;
import com.example.blps.repository.UserRepository;
import com.example.blps.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository,
                       final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createNewUser(UserCreateDTO createDTO) {
        return null;
    }
}