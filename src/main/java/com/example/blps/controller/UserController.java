package com.example.blps.controller;

import com.example.blps.dto.UserCreateDTO;
import com.example.blps.dto.request.UserCreateRequest;
import com.example.blps.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final IUserService userService;

    public UserController(final IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody final UserCreateRequest request) {
        UserCreateDTO createDTO = new UserCreateDTO(
                request.getUsername(),
                request.getPassword()
        );

        userService.createNewUser(createDTO);

        return ResponseEntity.
                noContent().
                build();
    }
}
