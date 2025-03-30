package ru.sfedu.ictis.sports_sections.service.Impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sfedu.ictis.sports_sections.dto.request.LocationRequestDto;
import ru.sfedu.ictis.sports_sections.dto.response.LocationResponseDto;
import ru.sfedu.ictis.sports_sections.entity.LocationEntity;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;
import ru.sfedu.ictis.sports_sections.exception.CustomException;
import ru.sfedu.ictis.sports_sections.exception.ErrorCodes;
import ru.sfedu.ictis.sports_sections.mapper.LocationMapper;
import ru.sfedu.ictis.sports_sections.repository.LocationRepository;
import ru.sfedu.ictis.sports_sections.repository.UserRepository;
import ru.sfedu.ictis.sports_sections.service.LocationService;
import ru.sfedu.ictis.sports_sections.service.UserService;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private final LocationMapper locationMapper;

    private final UserService userService;

    private final UserRepository userRepository;

    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper, UserService userService, UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public LocationResponseDto addLocation(LocationRequestDto locationRequestDto) {
        UserEntity userEntity = userRepository.findByEmail(userService.getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        if (!userEntity.getRole().equals("admin")) {
            throw new CustomException(ErrorCodes.NO_RIGHT);
        }

        String fullLocationName = locationRequestDto.getCountry() + ", " +
                locationRequestDto.getRegion() + ", " +
                locationRequestDto.getCity() + ", " +
                locationRequestDto.getStreet() + ", " +
                locationRequestDto.getBuilding();

        if (locationRepository.existsByFullLocation(fullLocationName)) {
            throw new CustomException(ErrorCodes.LOCATION_EXISTS);
        }

        LocationEntity locationEntity = locationMapper.toLocationEntity(locationRequestDto);
        locationEntity.setFullLocation(fullLocationName);
        locationRepository.save(locationEntity);

        return locationMapper.toLocationResponseDto(locationEntity);
    }

    @Override
    public void deleteLocationById(Long id) {
        UserEntity userEntity = userRepository.findByEmail(userService.getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        if (!userEntity.getRole().equals("admin")) {
            throw new CustomException(ErrorCodes.NO_RIGHT);
        }

        locationRepository.deleteById(id);
    }

    @Override
    public List<LocationResponseDto> getAllLocations() {
        return locationMapper.toListLocationsResponseDto(locationRepository.findAll());
    }

    @Override
    public LocationResponseDto getLocationById(Long id) {
        return locationMapper
                .toLocationResponseDto(locationRepository
                        .findLocationEntitiesById(id)
                        .orElseThrow(() -> new CustomException(ErrorCodes.LOCATION_NOT_EXISTS)));
    }
}
