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
import ru.sfedu.ictis.sports_sections.dto.request.AchievementTemplateDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.common.CustomSuccessResponse;
import ru.sfedu.ictis.sports_sections.entity.AchievementTemplateEntity;
import ru.sfedu.ictis.sports_sections.service.AchievementTemplateService;

import java.util.List;

@RestController
@RequestMapping("/achievement/template")
@Validated
public class AchievementTemplateController {
    private final AchievementTemplateService achievementTemplateService;

    public AchievementTemplateController(AchievementTemplateService achievementTemplateService) {
        this.achievementTemplateService = achievementTemplateService;
    }

    @PostMapping
    public ResponseEntity<CustomSuccessResponse<AchievementTemplateEntity>> createAchievementTemplate(
            @Valid
            @RequestBody
            AchievementTemplateDtoRequest achievement) {
        return ResponseEntity.ok(
                new CustomSuccessResponse<>(achievementTemplateService.createAchievement(achievement))
        );
    }

    @GetMapping
    public ResponseEntity<CustomSuccessResponse<List<AchievementTemplateEntity>>> getAllAchievementsTemplates() {
        return ResponseEntity.ok(new CustomSuccessResponse<>(achievementTemplateService.getAllAchievements()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<AchievementTemplateEntity>> putAchievementTemplate(
            @PathVariable
            @Positive
            Long id,
            @Valid
            @RequestBody
            AchievementTemplateDtoRequest achievement) {
        return ResponseEntity.ok(
                new CustomSuccessResponse<>(achievementTemplateService.putAchievement(id, achievement))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse> deleteAchievementTemplate(@PathVariable @Positive Long id) {
        achievementTemplateService.deleteAchievement(id);
        return ResponseEntity.ok(new CustomSuccessResponse<>());
    }
}
