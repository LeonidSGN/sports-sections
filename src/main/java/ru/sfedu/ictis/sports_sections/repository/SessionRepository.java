package ru.sfedu.ictis.sports_sections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sfedu.ictis.sports_sections.entity.SessionEntity;

import java.util.List;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    @Query("SELECT COUNT(s) FROM SessionEntity s WHERE s.trainer.id = :trainerId AND s.endDate < CURRENT_TIMESTAMP")
    long countPastSessionsByTrainer(@Param("trainerId") Long trainerId);

    @Query("""
        SELECT s FROM SessionEntity s
        JOIN s.section sec
        JOIN EnrollmentEntity e ON e.section.id = sec.id
        WHERE e.user.id = :userId AND e.status = 'register'
        """)
    List<SessionEntity> findUserSessions(@Param("userId") Long userId);

    List<SessionEntity> findByTrainerId(Long trainerId);
}
