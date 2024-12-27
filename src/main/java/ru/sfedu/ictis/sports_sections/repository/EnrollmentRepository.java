package ru.sfedu.ictis.sports_sections.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfedu.ictis.sports_sections.entity.EnrollmentEntity;
import ru.sfedu.ictis.sports_sections.entity.SectionEntity;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {

    Page<EnrollmentEntity> findAllByUser(UserEntity user, Pageable pageable);

    Page<EnrollmentEntity> findAllBySection(SectionEntity section, Pageable pageable);
}
