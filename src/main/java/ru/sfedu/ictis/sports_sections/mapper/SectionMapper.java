package ru.sfedu.ictis.sports_sections.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.sfedu.ictis.sports_sections.dto.request.SectionDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.GetSectionDtoResponse;
import ru.sfedu.ictis.sports_sections.entity.SectionEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SectionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "location", ignore = true)
    SectionEntity toSectionEntity(SectionDtoRequest sectionDtoRequest);

    GetSectionDtoResponse toGetSectionDtoResponse(SectionEntity sectionEntity);

    @Mapping(target = "categoryEntities", ignore = true)
    void updateSectionFromDto(SectionDtoRequest updateSectionDto, @MappingTarget SectionEntity sectionEntity);
}
