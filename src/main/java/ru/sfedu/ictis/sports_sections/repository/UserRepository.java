package ru.sfedu.ictis.sports_sections.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);

    boolean existsById(@NonNull Long id);

    Optional<UserEntity> findByEmail(String email);

    void deleteByEmail(String email);
}
