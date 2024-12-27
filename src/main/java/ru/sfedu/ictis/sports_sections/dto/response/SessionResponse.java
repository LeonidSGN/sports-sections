package ru.sfedu.ictis.sports_sections.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionResponse {
    private Long id;

    private String status;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long sectionId;

    private String sectionName;

    private Long trainerId;

    private String trainerName;
}
