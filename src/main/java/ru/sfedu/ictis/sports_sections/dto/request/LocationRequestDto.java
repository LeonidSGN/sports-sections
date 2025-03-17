package ru.sfedu.ictis.sports_sections.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import static ru.sfedu.ictis.sports_sections.exception.ValidationConstants.SECTION_LOCATION_NULL;
import static ru.sfedu.ictis.sports_sections.exception.ValidationConstants.SECTION_LOCATION_SIZE_NOT_VALID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequestDto {
    @Size(min = 3, max = 255, message = SECTION_LOCATION_SIZE_NOT_VALID)
    @NotBlank(message = SECTION_LOCATION_NULL)
    String country;

    @Size(min = 3, max = 255, message = SECTION_LOCATION_SIZE_NOT_VALID)
    @NotBlank(message = SECTION_LOCATION_NULL)
    String region;

    @Size(min = 3, max = 255, message = SECTION_LOCATION_SIZE_NOT_VALID)
    @NotBlank(message = SECTION_LOCATION_NULL)
    String city;

    @Size(min = 3, max = 255, message = SECTION_LOCATION_SIZE_NOT_VALID)
    @NotBlank(message = SECTION_LOCATION_NULL)
    String street;

    @Size(min = 1, max = 255, message = SECTION_LOCATION_SIZE_NOT_VALID)
    @NotBlank(message = SECTION_LOCATION_NULL)
    String building;
}