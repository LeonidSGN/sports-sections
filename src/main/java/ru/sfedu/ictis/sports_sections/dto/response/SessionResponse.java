package ru.sfedu.ictis.sports_sections.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionResponse {
    private Long id;

    private LocalTime startTime;

    private LocalTime endTime;

    private DayOfWeek day;

    private Long sectionId;

    private String sectionName;

    private Long trainerId;

    private String trainerName;
}
