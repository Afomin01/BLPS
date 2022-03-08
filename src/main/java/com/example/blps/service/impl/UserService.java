package com.example.blps.service.impl;

import com.example.blps.dto.ChangeUserRatingDTO;
import com.example.blps.dto.UserCreateDTO;
import com.example.blps.exception.UserCreationException;
import com.example.blps.model.User;
import com.example.blps.repository.UserRepository;
import com.example.blps.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        String username = createDTO.getUsername();
        if (userRepository.countByUsername(username) != 0) {
            throw new UserCreationException(String.format("User with username '%s' already exists", username));
        }

        String encodedPassword = passwordEncoder.encode(createDTO.getPassword());
        User user = new User(username, encodedPassword);
        user = userRepository.save(user);

        return user;
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.
                findByUsername(username).
                orElseThrow(
                        () -> new UsernameNotFoundException("User with username ")
                );
    }

    @Override
    public User changeUserRating(ChangeUserRatingDTO dto) {
        //TODO add new method
        User user = userRepository.getById(dto.getUserId());
        user.setRating(user.getRating() + dto.getRatingAddition());
        user = userRepository.save(user);

        return user;
    }
}
