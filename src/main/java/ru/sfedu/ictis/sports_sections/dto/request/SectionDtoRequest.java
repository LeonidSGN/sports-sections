package ru.sfedu.ictis.sports_sections.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.sfedu.ictis.sports_sections.exception.ValidationConstants;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
public class SectionDtoRequest {
    @NotBlank(message = ValidationConstants.AVATAR_NOT_NULL)
    private String avatar;

    @NotBlank(message = ValidationConstants.SECTION_NAME_NULL)
    @Size(min = 1, max = 100, message = ValidationConstants.SECTION_NAME_SIZE_NOT_VALID)
    private String name;

    @NotBlank(message = ValidationConstants.SECTION_DESCRIPTION_NULL)
    @Size(min = 1, max = 100, message = ValidationConstants.SECTION_DESCRIPTION_SIZE_NOT_VALID)
    private String description;

    @NotNull(message = ValidationConstants.SECTION_LOCATION_NULL)
    @Positive(message = ValidationConstants.ID_MUST_BE_POSITIVE)
    private Long locationId;

    @NotEmpty(message = ValidationConstants.SECTION_CATEGORIES_EMPTY)
    private Set<@NotBlank(message = ValidationConstants.SECTION_CATEGORIES_NULL) String> categories;
}
