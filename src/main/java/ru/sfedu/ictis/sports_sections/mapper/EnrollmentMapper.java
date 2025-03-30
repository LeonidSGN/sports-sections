package ru.sfedu.ictis.sports_sections.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.sfedu.ictis.sports_sections.dto.request.EnrollDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.EnrollStatusDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.GetEnrollmentsDtoResponse;
import ru.sfedu.ictis.sports_sections.entity.EnrollmentEntity;
import ru.sfedu.ictis.sports_sections.entity.SectionEntity;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnrollmentMapper {

    @Mapping(target = "user", source = "userEntity")
    @Mapping(target = "section", source = "sectionEntity")
    @Mapping(target = "trainer", source = "trainer")
    @Mapping(target = "status", source = "enrollDtoRequest.status")
    @Mapping(target = "id", ignore = true)
    EnrollmentEntity toEnrollmentEntity(
            EnrollDtoRequest enrollDtoRequest,
            UserEntity userEntity,
            SectionEntity sectionEntity,
            UserEntity trainer
    );

    @Mapping(target = "status", source = "status")
    void updateEnrollment(
            @MappingTarget EnrollmentEntity enrollmentEntity,
            EnrollStatusDtoRequest enrollStatusDtoRequest
    );

    GetEnrollmentsDtoResponse toGetEnrollmentsDtoResponse(EnrollmentEntity enrollment);
}
