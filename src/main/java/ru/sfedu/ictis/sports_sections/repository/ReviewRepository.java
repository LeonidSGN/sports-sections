package ru.sfedu.ictis.sports_sections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfedu.ictis.sports_sections.entity.ReviewEntity;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    Optional<ReviewEntity> findByUserIdAndSessionId(Long userId, Long sessionId);

    List<ReviewEntity> findBySession_Section_Id(Long sectionId);

    boolean existsByUserIdAndSessionId(Long userId, Long sessionId);
}
