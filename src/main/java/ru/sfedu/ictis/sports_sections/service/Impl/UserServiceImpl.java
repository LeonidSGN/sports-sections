package ru.sfedu.ictis.sports_sections.service.Impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sfedu.ictis.sports_sections.dto.request.ChangeRoleRequest;
import ru.sfedu.ictis.sports_sections.dto.request.PutUserDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.CategoryDtoResponse;
import ru.sfedu.ictis.sports_sections.dto.response.GetSectionDtoResponse;
import ru.sfedu.ictis.sports_sections.dto.response.UserResponse;
import ru.sfedu.ictis.sports_sections.entity.SectionEntity;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;
import ru.sfedu.ictis.sports_sections.exception.CustomException;
import ru.sfedu.ictis.sports_sections.exception.ErrorCodes;
import ru.sfedu.ictis.sports_sections.exception.ValidationConstants;
import ru.sfedu.ictis.sports_sections.mapper.CategoryMapper;
import ru.sfedu.ictis.sports_sections.mapper.LocationMapper;
import ru.sfedu.ictis.sports_sections.mapper.SectionMapper;
import ru.sfedu.ictis.sports_sections.mapper.UserMapper;
import ru.sfedu.ictis.sports_sections.repository.SectionRepository;
import ru.sfedu.ictis.sports_sections.repository.UserRepository;
import ru.sfedu.ictis.sports_sections.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final SectionRepository sectionRepository;

    private final UserMapper userMapper;

    private final SectionMapper sectionMapper;

    private final LocationMapper locationMapper;

    private final CategoryMapper categoryMapper;

    public UserServiceImpl(UserRepository userRepository, SectionRepository sectionRepository, UserMapper userMapper, SectionMapper sectionMapper, LocationMapper locationMapper, CategoryMapper categoryMapper) {
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.userMapper = userMapper;
        this.sectionMapper = sectionMapper;
        this.locationMapper = locationMapper;
        this.categoryMapper = categoryMapper;
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

    @Override
    public UserResponse changeUserRole(Long id, ChangeRoleRequest role) {
        UserEntity admin = userRepository.findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        if (!admin.getRole().equals("admin")) {
            throw new CustomException(ErrorCodes.NO_RIGHT);
        }

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        userEntity.setRole(role.getRole());
        userRepository.save(userEntity);
        return userMapper.toUserResponse(userEntity);
    }

    @Transactional
    @Override
    public GetSectionDtoResponse assignTrainerToSection(Long sectionId) {
        UserEntity trainer = userRepository.findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        SectionEntity section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new CustomException(ErrorCodes.SECTION_NOT_FOUND));

        if (!trainer.getRole().equals("trainer")) {
            throw new CustomException(ErrorCodes.NO_RIGHT);
        }

        if (!section.getTrainers().contains(trainer)) {
            section.getTrainers().add(trainer);
            trainer.getSectionEntities().add(section);
        }

        sectionRepository.save(section);

        GetSectionDtoResponse dto = sectionMapper.toGetSectionDtoResponse(section);

        dto.setLocation(locationMapper.toLocationResponseDto(section.getLocation()));

        Set<UserResponse> trainersList = section.getTrainers()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toSet());

        dto.setTrainers(trainersList);

        Set<CategoryDtoResponse> categoriesList = section.getCategoryEntities()
                .stream()
                .map(categoryMapper::toCategoryDtoResponse)
                .collect(Collectors.toSet());

        dto.setCategories(categoriesList);

        return dto;
    }

    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
