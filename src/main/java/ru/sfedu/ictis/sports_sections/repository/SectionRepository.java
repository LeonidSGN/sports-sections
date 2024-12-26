package ru.sfedu.ictis.sports_sections.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfedu.ictis.sports_sections.entity.SectionEntity;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;

public interface SectionRepository extends JpaRepository<SectionEntity, Long> {
    @EntityGraph(attributePaths = {"trainer", "categoryEntities"})
    Page<SectionEntity> findAll(Specification<SectionEntity> spec, Pageable pageable);

    Page<SectionEntity> findAllByTrainer(Pageable pageable, UserEntity trainer);
}
