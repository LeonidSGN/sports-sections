package ru.sfedu.ictis.sports_sections.service.Impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sfedu.ictis.sports_sections.dto.request.PutSessionDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.SessionDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.SessionResponse;
import ru.sfedu.ictis.sports_sections.entity.SectionEntity;
import ru.sfedu.ictis.sports_sections.entity.SessionEntity;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;
import ru.sfedu.ictis.sports_sections.exception.CustomException;
import ru.sfedu.ictis.sports_sections.exception.ErrorCodes;
import ru.sfedu.ictis.sports_sections.mapper.SessionMapper;
import ru.sfedu.ictis.sports_sections.repository.SectionRepository;
import ru.sfedu.ictis.sports_sections.repository.SessionRepository;
import ru.sfedu.ictis.sports_sections.repository.UserRepository;
import ru.sfedu.ictis.sports_sections.service.SessionService;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    private final UserRepository userRepository;

    private final SectionRepository sectionRepository;

    private final SessionMapper sessionMapper;

    public SessionServiceImpl(
            SessionRepository sessionRepository,
            UserRepository userRepository,
            SectionRepository sectionRepository,
            SessionMapper sessionMapper) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.sessionMapper = sessionMapper;
    }

    @Override
    public Long addSession(SessionDtoRequest sessionDtoRequest) {
        UserEntity trainer = userRepository
                .findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        if (!trainer.getRole().equals("trainer")) {
            throw new CustomException(ErrorCodes.NO_RIGHTS_FOR_CREATE);
        }

        boolean trainerAssigned = sectionRepository.existsByIdAndTrainers_Id(
                sessionDtoRequest.getSectionId(),
                trainer.getId()
        );

        if (!trainerAssigned) {
            throw new CustomException(ErrorCodes.TRAINER_NOT_ASSIGNED_TO_SECTION);
        }

        SectionEntity sectionEntity = sectionRepository
                .findById(sessionDtoRequest.getSectionId())
                .orElseThrow(() -> new CustomException(ErrorCodes.SECTION_NOT_FOUND));

        SessionEntity sessionEntity = sessionMapper.toSessionEntity(sessionDtoRequest);
        sessionEntity.setSection(sectionEntity);
        sessionEntity.setTrainer(trainer);
        sessionRepository.save(sessionEntity);
        return sessionEntity.getId();
    }

    @Override
    public List<SessionResponse> getAllSessions() {
        List<SessionEntity> sessions = sessionRepository.findAll();
        return sessions
                .stream()
                .map(entity -> {
                    SessionResponse dto = sessionMapper.toSessionResponse(entity);
                    dto.setTrainerName(entity.getTrainer().getName());
                    dto.setTrainerId(entity.getTrainer().getId());
                    dto.setSectionName(entity.getSection().getName());
                    dto.setSectionId(entity.getSection().getId());

                    return dto;
                }).toList();
    }

    @Override
    public List<SessionResponse> getTrainerSchedule(Long trainerId) {
        UserEntity trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        List<SessionEntity> sessions = sessionRepository.findByTrainerId(trainer.getId());
        return sessions
                .stream()
                .map(entity -> {
                    SessionResponse dto = sessionMapper.toSessionResponse(entity);
                    dto.setTrainerName(entity.getTrainer().getName());
                    dto.setTrainerId(entity.getTrainer().getId());
                    dto.setSectionName(entity.getSection().getName());
                    dto.setSectionId(entity.getSection().getId());

                    return dto;
                }).toList();
    }

    @Override
    public List<SessionResponse> getUserSchedule(Long userId) {
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        List<SessionEntity> sessionEntities = sessionRepository.findByUserEnrollments(user.getId());
        return sessionEntities
                .stream()
                .map(entity -> {
                    SessionResponse dto = sessionMapper.toSessionResponse(entity);
                    dto.setTrainerName(entity.getTrainer().getName());
                    dto.setTrainerId(entity.getTrainer().getId());
                    dto.setSectionName(entity.getSection().getName());
                    dto.setSectionId(entity.getSection().getId());

                    return dto;
                }).toList();
    }

    @Override
    public void putSession(Long id, PutSessionDtoRequest sessionDtoRequest) {
        SessionEntity sessionEntity = sessionRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.SESSION_NOT_FOUND));

        sessionMapper.updateSessionFromDto(sessionDtoRequest, sessionEntity);
        sessionRepository.save(sessionEntity);
    }

    @Override
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
