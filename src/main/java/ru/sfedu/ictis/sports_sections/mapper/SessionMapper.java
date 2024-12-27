package ru.sfedu.ictis.sports_sections.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.sfedu.ictis.sports_sections.dto.request.PutSessionDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.SessionDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.SessionResponse;
import ru.sfedu.ictis.sports_sections.entity.SessionEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SessionMapper {
    SessionEntity toSessionEntity(SessionDtoRequest sessionDtoRequest);

    SessionResponse toSessionResponse(SessionEntity sessionEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "section", ignore = true)
    @Mapping(target = "trainer", ignore = true)
    void updateSessionFromDto(PutSessionDtoRequest updateSessionDto, @MappingTarget SessionEntity sessionEntity);
}
