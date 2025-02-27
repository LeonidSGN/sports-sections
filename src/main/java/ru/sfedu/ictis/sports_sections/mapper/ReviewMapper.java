package ru.sfedu.ictis.sports_sections.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.sfedu.ictis.sports_sections.dto.request.PutReviewDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.ReviewDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.ReviewDtoResponse;
import ru.sfedu.ictis.sports_sections.entity.ReviewEntity;
import ru.sfedu.ictis.sports_sections.entity.SessionEntity;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {

    @Mapping(target = "user", source = "userEntity")
    @Mapping(target = "session", source = "sessionEntity")
    @Mapping(target = "id", ignore = true)
    ReviewEntity toReviewEntity(ReviewDtoRequest request,
                                UserEntity userEntity,
                                SessionEntity sessionEntity);

    ReviewDtoResponse toReviewDtoResponse(ReviewEntity reviewEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "session", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    void updateReviewFromDto(PutReviewDtoRequest request, @MappingTarget ReviewEntity reviewEntity);
}
