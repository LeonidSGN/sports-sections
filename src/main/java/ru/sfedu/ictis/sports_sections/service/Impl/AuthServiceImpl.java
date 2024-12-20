package ru.sfedu.ictis.sports_sections.service.Impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sfedu.ictis.sports_sections.dto.request.AuthUserDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.RegisterUserDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.LoginUserResponse;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;
import ru.sfedu.ictis.sports_sections.exception.CustomException;
import ru.sfedu.ictis.sports_sections.exception.ErrorCodes;
import ru.sfedu.ictis.sports_sections.mapper.UserMapper;
import ru.sfedu.ictis.sports_sections.repository.UserRepository;
import ru.sfedu.ictis.sports_sections.security.JwtUtil;
import ru.sfedu.ictis.sports_sections.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final UserMapper userMapper;

    public AuthServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
    }

    @Override
    public LoginUserResponse register(RegisterUserDtoRequest userDto) {
        if (userRepo.existsByEmail(userDto.getEmail())) {
            throw new CustomException(ErrorCodes.USER_ALREADY_EXISTS);
        }

        UserEntity user = userMapper.toUserEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepo.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getId());

        LoginUserResponse response = userMapper.toLoginUserResponse(user);
        response.setToken(token);
        return response;
    }

    @Override
    public LoginUserResponse login(AuthUserDtoRequest authDtoRequest) {
        UserEntity user = userRepo.findByEmail(authDtoRequest.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        if (!passwordEncoder.matches(authDtoRequest.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCodes.USER_PASSWORD_NOT_VALID);
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getId());

        LoginUserResponse response = userMapper.toLoginUserResponse(user);
        response.setToken(token);
        return response;
    }
}
