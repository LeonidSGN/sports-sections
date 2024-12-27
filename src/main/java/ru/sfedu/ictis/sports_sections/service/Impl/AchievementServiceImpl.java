package ru.sfedu.ictis.sports_sections.service.Impl;

import org.springframework.stereotype.Service;
import ru.sfedu.ictis.sports_sections.dto.request.AchievementDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.AchievementDtoResponse;
import ru.sfedu.ictis.sports_sections.entity.AchievementEntity;
import ru.sfedu.ictis.sports_sections.entity.AchievementTemplateEntity;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;
import ru.sfedu.ictis.sports_sections.exception.CustomException;
import ru.sfedu.ictis.sports_sections.exception.ErrorCodes;
import ru.sfedu.ictis.sports_sections.repository.AchievementRepository;
import ru.sfedu.ictis.sports_sections.repository.AchievementTemplateRepository;
import ru.sfedu.ictis.sports_sections.repository.UserRepository;
import ru.sfedu.ictis.sports_sections.service.AchievementService;

import java.time.LocalDateTime;

@Service
public class AchievementServiceImpl implements AchievementService {
    private final AchievementRepository achievementRepository;

    private final AchievementTemplateRepository achievementTemplateRepository;

    private final UserRepository userRepository;

    public AchievementServiceImpl(
            AchievementRepository achievementRepository,
            AchievementTemplateRepository achievementTemplateRepository,
            UserRepository userRepository) {
        this.achievementRepository = achievementRepository;
        this.achievementTemplateRepository = achievementTemplateRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AchievementDtoResponse addAchievement(AchievementDtoRequest achievementDtoRequest) {
        AchievementTemplateEntity achievementTemplate = achievementTemplateRepository
                .findById(achievementDtoRequest.getAchievementTemplateId())
                .orElseThrow(() -> new CustomException(ErrorCodes.ACHIEVEMENT_TEMPLATE_NOT_FOUND));

        UserEntity user = userRepository
                .findById(achievementDtoRequest.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        AchievementEntity achievement = new AchievementEntity();
        achievement.setAchievement(achievementTemplate);
        achievement.setUser(user);
        achievement.setAwardedAt(LocalDateTime.now());
        achievementRepository.save(achievement);

        AchievementDtoResponse result = new AchievementDtoResponse();
        result.setId(achievement.getId());
        result.setAchievementId(achievement.getAchievement().getId());
        result.setUserId(achievement.getUser().getId());
        result.setAwardedAt(achievement.getAwardedAt());
        return result;
    }

    @Override
    public void deleteAchievement(Long id) {
        achievementRepository.deleteById(id);
    }
}
