package ru.sfedu.ictis.sports_sections.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AchievementDtoRequest {
    @Positive
    private Long userId;

    @Positive
    private Long achievementTemplateId;
}
