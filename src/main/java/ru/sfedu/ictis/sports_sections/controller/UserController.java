package ru.sfedu.ictis.sports_sections.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sfedu.ictis.sports_sections.dto.request.PutUserDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.UserResponse;
import ru.sfedu.ictis.sports_sections.dto.response.common.CustomSuccessResponse;
import ru.sfedu.ictis.sports_sections.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<CustomSuccessResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(new CustomSuccessResponse<>(userService.getAllUsers()));
    }

    @PutMapping
    public ResponseEntity<CustomSuccessResponse<UserResponse>> replaceUser(
            @Valid
            @RequestBody
            PutUserDtoRequest putUserDto) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(userService.replaceUser(putUserDto)));
    }

    @DeleteMapping
    public ResponseEntity<CustomSuccessResponse> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.ok(new CustomSuccessResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<UserResponse>> getUserInfoById(
            @PathVariable
            Long id) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(userService.getUserInfoById(id)));
    }

    @GetMapping("/info")
    public ResponseEntity<CustomSuccessResponse<UserResponse>> getUserInfo() {
        return ResponseEntity.ok(new CustomSuccessResponse<>(userService.getUserInfo()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<UserResponse>> replaceUserById(@PathVariable
                                                                               Long id,
                                                                               @Valid
                                                                               @RequestBody
                                                                               PutUserDtoRequest putUserDto) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(userService.replaceUserById(id, putUserDto)));
    }
}
