package com.example.blps.service.impl;

import com.example.blps.model.User;
import com.example.blps.model.xml.UserDetailsXmlImpl;
import com.example.blps.model.xml.UsersXml;
import com.example.blps.repository.UserRepository;
import com.example.blps.service.IUserService;
import com.example.blps.util.UserLoader;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements UserDetailsService, IUserService {
    private UserRepository userRepository;

    private Map<String, UserDetailsXmlImpl> users;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void loadUsersFromXmlFile() {
        UsersXml loadedUsers = UserLoader.loadUsers();

        users = loadedUsers.getUsers().stream()
                .collect(Collectors.toMap(UserDetailsXmlImpl::getUsername, Function.identity()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return users.get(username);
    }

    public UserDetails loadUserById(UUID uuid) {
        return users.values().stream()
                .filter(user -> user.getId().equals(uuid.toString()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No user with id " + uuid));
    }

    public User loadUserEntity(Principal principal) {
        String id = users.values().stream()
                .filter(user -> user.getUsername().equals(principal.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No user username " + principal.getName()))
                .getId();

        return userRepository.getById(UUID.fromString(id));
    }

    @Override
    public User getUserById(UUID uuid) {
        return null;
    }
}
