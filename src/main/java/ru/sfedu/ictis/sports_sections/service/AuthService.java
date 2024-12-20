package ru.sfedu.ictis.sports_sections.service;

import ru.sfedu.ictis.sports_sections.dto.request.AuthUserDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.RegisterUserDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.LoginUserResponse;

public interface AuthService {
    LoginUserResponse register(RegisterUserDtoRequest userDto);

    LoginUserResponse login(AuthUserDtoRequest authDtoRequest);
}
