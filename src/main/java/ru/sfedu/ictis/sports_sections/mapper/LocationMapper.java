package ru.sfedu.ictis.sports_sections.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.sfedu.ictis.sports_sections.dto.request.LocationRequestDto;
import ru.sfedu.ictis.sports_sections.dto.response.LocationResponseDto;
import ru.sfedu.ictis.sports_sections.entity.LocationEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fullLocation", ignore = true)
    LocationEntity toLocationEntity(LocationRequestDto locationRequestDto);

    LocationResponseDto toLocationResponseDto(LocationEntity locationEntity);

    List<LocationResponseDto> toListLocationsResponseDto(List<LocationEntity> locationEntities);
}
