package ru.sfedu.ictis.sports_sections.service;

import ru.sfedu.ictis.sports_sections.dto.request.PutSessionDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.SessionDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.SessionResponse;

import java.util.List;

public interface SessionService {
    Long addSession(SessionDtoRequest sessionDtoRequest);

    List<SessionResponse> getAllSessions();

    void putSession(Long id, PutSessionDtoRequest putSessionDtoRequest);

    void deleteSession(Long id);
}
