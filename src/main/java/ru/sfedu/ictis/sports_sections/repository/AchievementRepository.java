package ru.sfedu.ictis.sports_sections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfedu.ictis.sports_sections.entity.AchievementEntity;

public interface AchievementRepository extends JpaRepository<AchievementEntity, Long> {

}
