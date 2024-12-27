package ru.sfedu.ictis.sports_sections.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sfedu.ictis.sports_sections.dto.request.AchievementDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.AchievementDtoResponse;
import ru.sfedu.ictis.sports_sections.dto.response.common.CustomSuccessResponse;
import ru.sfedu.ictis.sports_sections.service.AchievementService;

@RestController
@RequestMapping("/achievement")
@Validated
public class AchievementController {
    private final AchievementService achievementService;

    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @PostMapping
    public ResponseEntity<CustomSuccessResponse<AchievementDtoResponse>> addAchievement(
            @RequestBody
            @Valid
            AchievementDtoRequest achievement) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(achievementService.addAchievement(achievement)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse> deleteAchievement(@PathVariable @Positive Long id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.ok(new CustomSuccessResponse<>());
    }
}
