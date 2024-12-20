package ru.sfedu.ictis.sports_sections.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;
import ru.sfedu.ictis.sports_sections.exception.CustomException;
import ru.sfedu.ictis.sports_sections.exception.ErrorCodes;
import ru.sfedu.ictis.sports_sections.repository.UserRepository;

import java.util.Collections;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity userEntity = userRepo.findByEmail(email).orElseThrow(() ->
                new CustomException(ErrorCodes.USER_NOT_FOUND));
        return new User(userEntity.getEmail(), userEntity.getPassword(), Collections.emptyList());
    }
}
