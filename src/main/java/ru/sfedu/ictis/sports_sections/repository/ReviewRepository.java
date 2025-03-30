package ru.sfedu.ictis.sports_sections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfedu.ictis.sports_sections.entity.ReviewEntity;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    Optional<ReviewEntity> findByUserIdAndSectionId(Long userId, Long sectionId);

    List<ReviewEntity> findBySectionId(Long sectionId);

    boolean existsByUserIdAndSectionId(Long userId, Long sectionId);
}
