package ru.sfedu.ictis.sports_sections.service;

import ru.sfedu.ictis.sports_sections.dto.request.PutUserDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.GetSectionDtoResponse;
import ru.sfedu.ictis.sports_sections.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    UserResponse replaceUser(PutUserDtoRequest putUserDto);

    void deleteUser();

    UserResponse getUserInfoById(Long id);

    UserResponse getUserInfo();

    UserResponse replaceUserById(Long id, PutUserDtoRequest putUserDto);

    GetSectionDtoResponse assignTrainerToSection(Long sectionId);

    String getCurrentUserEmail();
}
