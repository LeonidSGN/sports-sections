package ru.sfedu.ictis.sports_sections.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sfedu.ictis.sports_sections.exception.ValidationConstants;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AchievementTemplateDtoRequest {
    @NotBlank(message = ValidationConstants.ACHIEVEMENT_TITLE_NULL)
    @Size(min = 3, max = 100, message = ValidationConstants.ACHIEVEMENT_TITLE_SIZE_NOT_VALID)
    private String title;

    @NotBlank(message = ValidationConstants.ACHIEVEMENT_DESCRIPTION_NULL)
    @Size(min = 3, max = 150, message = ValidationConstants.ACHIEVEMENT_DESCRIPTION_SIZE_NOT_VALID)
    private String description;
}
