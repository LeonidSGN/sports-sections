package ru.sfedu.ictis.sports_sections.service;

import ru.sfedu.ictis.sports_sections.dto.request.AchievementTemplateDtoRequest;
import ru.sfedu.ictis.sports_sections.entity.AchievementTemplateEntity;

import java.util.List;

public interface AchievementTemplateService {
    AchievementTemplateEntity createAchievement(AchievementTemplateDtoRequest achievement);

    List<AchievementTemplateEntity> getAllAchievements();

    AchievementTemplateEntity putAchievement(Long id, AchievementTemplateDtoRequest achievement);

    void deleteAchievement(Long id);
}
