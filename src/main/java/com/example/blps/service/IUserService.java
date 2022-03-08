package com.example.blps.service;

import com.example.blps.dto.ChangeUserRatingDTO;
import com.example.blps.model.User;

import java.security.Principal;
import java.util.UUID;

public interface IUserService {
    User getUserById(UUID uuid);

    User changeUserRating(ChangeUserRatingDTO dto);

    User loadUserEntity(Principal principal);
}
