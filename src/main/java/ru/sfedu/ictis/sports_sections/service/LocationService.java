package ru.sfedu.ictis.sports_sections.service;

import ru.sfedu.ictis.sports_sections.dto.request.LocationRequestDto;
import ru.sfedu.ictis.sports_sections.dto.response.LocationResponseDto;

import java.util.List;

public interface LocationService {
    LocationResponseDto addLocation(LocationRequestDto locationRequestDto);

    void deleteLocationById(Long id);

    List<LocationResponseDto> getAllLocations();

    LocationResponseDto getLocationById(Long id);
}
