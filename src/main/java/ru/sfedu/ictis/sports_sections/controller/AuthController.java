package ru.sfedu.ictis.sports_sections.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sfedu.ictis.sports_sections.dto.request.AuthUserDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.RegisterUserDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.LoginUserResponse;
import ru.sfedu.ictis.sports_sections.dto.response.common.CustomSuccessResponse;
import ru.sfedu.ictis.sports_sections.service.AuthService;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomSuccessResponse<LoginUserResponse>> register(
            @Valid
            @RequestBody
            RegisterUserDtoRequest userDto) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(authService.register(userDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<CustomSuccessResponse<LoginUserResponse>> login(
            @Valid
            @RequestBody
            AuthUserDtoRequest authDtoRequest) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(authService.login(authDtoRequest)));
    }
}
