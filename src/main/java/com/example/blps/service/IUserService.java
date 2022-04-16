package com.example.blps.service;

import com.example.blps.dto.ChangeUserRatingDTO;
import com.example.blps.exception.NotFoundException;
import com.example.blps.model.User;

import java.security.Principal;
import java.util.UUID;

public interface IUserService {
    User getUserById(UUID uuid) throws NotFoundException;

    User changeUserRating(ChangeUserRatingDTO dto) throws NotFoundException;

    User loadUserEntityByPrincipal(Principal principal);
}
