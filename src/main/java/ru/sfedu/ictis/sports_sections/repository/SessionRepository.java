package ru.sfedu.ictis.sports_sections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sfedu.ictis.sports_sections.entity.SessionEntity;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

}
