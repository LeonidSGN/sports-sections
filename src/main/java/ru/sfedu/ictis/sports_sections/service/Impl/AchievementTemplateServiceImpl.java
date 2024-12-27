package ru.sfedu.ictis.sports_sections.service.Impl;

import org.springframework.stereotype.Service;
import ru.sfedu.ictis.sports_sections.dto.request.AchievementTemplateDtoRequest;
import ru.sfedu.ictis.sports_sections.entity.AchievementTemplateEntity;
import ru.sfedu.ictis.sports_sections.exception.CustomException;
import ru.sfedu.ictis.sports_sections.exception.ErrorCodes;
import ru.sfedu.ictis.sports_sections.mapper.AchievementTemplateMapper;
import ru.sfedu.ictis.sports_sections.repository.AchievementTemplateRepository;
import ru.sfedu.ictis.sports_sections.service.AchievementTemplateService;

import java.util.List;

@Service
public class AchievementTemplateServiceImpl implements AchievementTemplateService {
    private final AchievementTemplateRepository achievementTemplateRepository;

    private final AchievementTemplateMapper achievementTemplateMapper;

    public AchievementTemplateServiceImpl(
            AchievementTemplateRepository achievementTemplateRepository,
            AchievementTemplateMapper achievementTemplateMapper) {
        this.achievementTemplateRepository = achievementTemplateRepository;
        this.achievementTemplateMapper = achievementTemplateMapper;
    }

    @Override
    public AchievementTemplateEntity createAchievement(AchievementTemplateDtoRequest achievement) {
        if (achievementTemplateRepository.existsByTitle(achievement.getTitle())) {
            throw new CustomException(ErrorCodes.ACHIEVEMENT_TEMPLATE_TITLE_EXIST);
        }

        AchievementTemplateEntity achievementTemplate = achievementTemplateMapper
                .toAchievementTemplateEntity(achievement);

        achievementTemplateRepository.save(achievementTemplate);
        return achievementTemplate;
    }

    @Override
    public List<AchievementTemplateEntity> getAllAchievements() {
        return achievementTemplateRepository.findAll();
    }

    @Override
    public AchievementTemplateEntity putAchievement(Long id, AchievementTemplateDtoRequest achievement) {
        AchievementTemplateEntity achievementTemplate = achievementTemplateRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.ACHIEVEMENT_TEMPLATE_NOT_FOUND));

        achievementTemplateMapper.updateAchievementTemplateFromDto(achievement, achievementTemplate);
        achievementTemplateRepository.save(achievementTemplate);
        return achievementTemplate;
    }

    @Override
    public void deleteAchievement(Long id) {
        achievementTemplateRepository.deleteById(id);
    }
}
