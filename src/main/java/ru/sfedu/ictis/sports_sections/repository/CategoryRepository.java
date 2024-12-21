package ru.sfedu.ictis.sports_sections.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfedu.ictis.sports_sections.entity.CategoryEntity;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Boolean existsByName(String name);

    Optional<CategoryEntity> findById(@NonNull Long id);

    void deleteById(@NonNull  Long id);
}
