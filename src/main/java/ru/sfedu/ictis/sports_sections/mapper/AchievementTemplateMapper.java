package ru.sfedu.ictis.sports_sections.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.sfedu.ictis.sports_sections.dto.request.AchievementTemplateDtoRequest;
import ru.sfedu.ictis.sports_sections.entity.AchievementTemplateEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AchievementTemplateMapper {
    AchievementTemplateEntity toAchievementTemplateEntity(AchievementTemplateDtoRequest achievementTemplateDtoRequest);

    @Mapping(target = "id", ignore = true)
    void updateAchievementTemplateFromDto(
            AchievementTemplateDtoRequest updateDto,
            @MappingTarget AchievementTemplateEntity achievementTemplate
    );
}
