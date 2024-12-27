package ru.sfedu.ictis.sports_sections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfedu.ictis.sports_sections.entity.AchievementTemplateEntity;

public interface AchievementTemplateRepository extends JpaRepository<AchievementTemplateEntity, Long> {
    Boolean existsByTitle(String title);
}
