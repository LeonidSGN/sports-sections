package ru.sfedu.ictis.sports_sections.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sfedu.ictis.sports_sections.dto.request.LocationRequestDto;
import ru.sfedu.ictis.sports_sections.dto.response.LocationResponseDto;
import ru.sfedu.ictis.sports_sections.dto.response.common.CustomSuccessResponse;
import ru.sfedu.ictis.sports_sections.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/location")
@Validated
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<CustomSuccessResponse<List<LocationResponseDto>>> getAllLocations() {
        return ResponseEntity.ok(new CustomSuccessResponse<>(locationService.getAllLocations()));
    }

    @PostMapping
    public ResponseEntity<CustomSuccessResponse<LocationResponseDto>> addLocation(
            @Valid
            @RequestBody
            LocationRequestDto locationRequestDto) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(locationService.addLocation(locationRequestDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse> deleteLocationById(
            @PathVariable
            @Positive
            Long id) {
        locationService.deleteLocationById(id);
        return ResponseEntity.ok(new CustomSuccessResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<LocationResponseDto>> getLocationById(
            @PathVariable
            @Positive
            Long id) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(locationService.getLocationById(id)));
    }
}
