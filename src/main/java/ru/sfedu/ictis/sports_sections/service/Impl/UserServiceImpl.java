package ru.sfedu.ictis.sports_sections.service.Impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sfedu.ictis.sports_sections.dto.request.PutUserDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.UserResponse;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;
import ru.sfedu.ictis.sports_sections.exception.CustomException;
import ru.sfedu.ictis.sports_sections.exception.ErrorCodes;
import ru.sfedu.ictis.sports_sections.mapper.UserMapper;
import ru.sfedu.ictis.sports_sections.repository.UserRepository;
import ru.sfedu.ictis.sports_sections.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return userMapper.toListUserResponse(users);
    }

    @Override
    public UserResponse replaceUser(PutUserDtoRequest putUserDto) {
        UserEntity userEntity = userRepository.findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));
        userMapper.updateUserFromDto(putUserDto, userEntity);
        userRepository.save(userEntity);
        return userMapper.toUserResponse(userEntity);
    }

    @Override
    @Transactional
    public void deleteUser() {
        userRepository.deleteByEmail(getCurrentUserEmail());
    }

    @Override
    public UserResponse getUserInfoById(Long id) {
        return userMapper.toUserResponse(userRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.MAX_UPLOAD_SIZE_EXCEEDED)));
    }

    @Override
    public UserResponse getUserInfo() {
        return userMapper.toUserResponse(userRepository.findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND)));
    }

    @Override
    public UserResponse replaceUserById(Long id, PutUserDtoRequest putUserDto) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));
        userMapper.updateUserFromDto(putUserDto, userEntity);
        userRepository.save(userEntity);
        return userMapper.toUserResponse(userEntity);
    }

    private String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
