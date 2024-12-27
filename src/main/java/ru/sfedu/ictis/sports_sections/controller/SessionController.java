package ru.sfedu.ictis.sports_sections.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sfedu.ictis.sports_sections.dto.request.PutSessionDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.SessionDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.CreateResponse;
import ru.sfedu.ictis.sports_sections.dto.response.SessionResponse;
import ru.sfedu.ictis.sports_sections.dto.response.common.CustomSuccessResponse;
import ru.sfedu.ictis.sports_sections.service.SessionService;

import java.util.List;

@RestController
@RequestMapping ("/session")
@Validated
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<CreateResponse> addSession(
            @Valid
            @RequestBody
            SessionDtoRequest sessionDtoRequest) {
        return ResponseEntity.ok(new CreateResponse(sessionService.addSession(sessionDtoRequest)));
    }

    @GetMapping
    public ResponseEntity<CustomSuccessResponse<List<SessionResponse>>> getAllSessions() {
        return ResponseEntity.ok(new CustomSuccessResponse<>(sessionService.getAllSessions()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<SessionResponse>> putSession(
            @PathVariable
            @Positive
            Long id,
            @Valid
            @RequestBody
            PutSessionDtoRequest sessionDtoRequest) {
        sessionService.putSession(id, sessionDtoRequest);
        return ResponseEntity.ok(new CustomSuccessResponse<>());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse> deleteSession(
            @PathVariable
            @Positive
            Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.ok(new CustomSuccessResponse<>());
    }
}
