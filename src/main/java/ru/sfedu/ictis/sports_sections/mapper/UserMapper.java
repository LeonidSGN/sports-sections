package ru.sfedu.ictis.sports_sections.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.sfedu.ictis.sports_sections.dto.request.PutUserDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.RegisterUserDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.LoginUserResponse;
import ru.sfedu.ictis.sports_sections.dto.response.UserResponse;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity toUserEntity(RegisterUserDtoRequest userDto);

    @Mapping(target = "token", ignore = true)
    LoginUserResponse toLoginUserResponse(UserEntity userEntity);

    UserResponse toUserResponse(UserEntity userEntity);

    List<UserResponse> toListUserResponse(List<UserEntity> userEntities);

    @Mapping(target = "id", ignore = true)
    void updateUserFromDto(PutUserDtoRequest putUserDto, @MappingTarget UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserEntity toUserEntity(PutUserDtoRequest putUserDto);
}
