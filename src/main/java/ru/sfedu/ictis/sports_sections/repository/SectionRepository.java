package ru.sfedu.ictis.sports_sections.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.sfedu.ictis.sports_sections.entity.LocationEntity;
import ru.sfedu.ictis.sports_sections.entity.SectionEntity;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;

import javax.xml.stream.Location;

public interface SectionRepository extends JpaRepository<SectionEntity, Long> {
    @EntityGraph(attributePaths = {"trainers", "categoryEntities"})
    Page<SectionEntity> findAll(Specification<SectionEntity> spec, Pageable pageable);

    Page<SectionEntity> findAllByTrainersContaining(Pageable pageable, UserEntity trainer);

    boolean existsByNameAndLocation(String name, LocationEntity location);

    @Query("SELECT DISTINCT s FROM SectionEntity s JOIN s.trainers t")
    Page<SectionEntity> findAllWithTrainers(Pageable pageable);
}
