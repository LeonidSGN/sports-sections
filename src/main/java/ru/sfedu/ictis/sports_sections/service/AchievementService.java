package ru.sfedu.ictis.sports_sections.service;

import ru.sfedu.ictis.sports_sections.dto.request.AchievementDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.AchievementDtoResponse;

public interface AchievementService {
    AchievementDtoResponse addAchievement(AchievementDtoRequest achievementDtoRequest);

    void deleteAchievement(Long id);
}
