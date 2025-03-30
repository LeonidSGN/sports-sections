package ru.sfedu.ictis.sports_sections.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.sfedu.ictis.sports_sections.exception.ValidationConstants;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
public class SessionDtoRequest {
    @NotNull(message = ValidationConstants.SECTION_ID_NULL)
    @Positive
    private Long sectionId;

    @NotNull(message = ValidationConstants.TIME_NULL)
    private LocalTime startTime;

    @NotNull(message = ValidationConstants.TIME_NULL)
    private LocalTime endTime;

    @NotNull(message = ValidationConstants.DAY_NULL)
    private DayOfWeek day;
}
