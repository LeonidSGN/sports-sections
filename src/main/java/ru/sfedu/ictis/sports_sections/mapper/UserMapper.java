package ru.sfedu.ictis.sports_sections.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.sfedu.ictis.sports_sections.dto.request.RegisterUserDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.LoginUserResponse;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity toUserEntity(RegisterUserDtoRequest userDto);

    @Mapping(target = "token", ignore = true)
    LoginUserResponse toLoginUserResponse(UserEntity userEntity);
}
