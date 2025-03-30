package ru.sfedu.ictis.sports_sections.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sfedu.ictis.sports_sections.entity.EnrollmentEntity;
import ru.sfedu.ictis.sports_sections.entity.SectionEntity;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {

    Page<EnrollmentEntity> findAllByUser(UserEntity user, Pageable pageable);

    Page<EnrollmentEntity> findAllBySection(SectionEntity section, Pageable pageable);

    @Query("""
        SELECT COUNT(e) > 0 FROM EnrollmentEntity e\s
        WHERE e.user.id = :userId\s
          AND e.section.id = :sectionId\s
          AND e.status = :status
          """)
    boolean existsByUserIdAndSectionIdAndStatus(
            @Param("userId") Long userId,
            @Param("sectionId") Long sectionId,
            @Param("status") String status
    );

    List<EnrollmentEntity> findAllByUser(UserEntity user);
}
