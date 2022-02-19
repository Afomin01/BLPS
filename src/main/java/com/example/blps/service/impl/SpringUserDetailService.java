package com.example.blps.service.impl;

import com.example.blps.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpringUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public SpringUserDetailService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.
                findByUsername(username).
                orElseThrow(
                        () -> new UsernameNotFoundException("User with username "+ username +" not exists")
                );
    }
}
