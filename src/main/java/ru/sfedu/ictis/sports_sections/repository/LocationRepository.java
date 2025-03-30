package ru.sfedu.ictis.sports_sections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfedu.ictis.sports_sections.entity.LocationEntity;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    boolean existsByFullLocation(String fullLocation);

    Optional<LocationEntity> findLocationEntitiesById(Long id);
}
