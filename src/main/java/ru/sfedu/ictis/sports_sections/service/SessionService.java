package ru.sfedu.ictis.sports_sections.service;

import ru.sfedu.ictis.sports_sections.dto.request.PutSessionDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.SessionDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.SessionResponse;

import java.util.List;

public interface SessionService {
    Long addSession(SessionDtoRequest sessionDtoRequest);

    List<SessionResponse> getAllSessions();

    List<SessionResponse> getTrainerSchedule(Long trainerId);

    List<SessionResponse> getUserSchedule(Long userId);

    void putSession(Long id, PutSessionDtoRequest putSessionDtoRequest);

    void deleteSession(Long id);
}
