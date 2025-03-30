package ru.sfedu.ictis.sports_sections.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.sfedu.ictis.sports_sections.exception.ValidationConstants;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
public class PutSessionDtoRequest {
    @NotNull(message = ValidationConstants.TIME_NULL)
    private LocalTime startTime;

    @NotNull(message = ValidationConstants.TIME_NULL)
    private LocalTime endTime;

    @NotNull(message = ValidationConstants.DAY_NULL)
    private DayOfWeek day;
}
