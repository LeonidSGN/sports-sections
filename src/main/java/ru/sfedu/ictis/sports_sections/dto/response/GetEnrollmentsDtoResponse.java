package ru.sfedu.ictis.sports_sections.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetEnrollmentsDtoResponse {
    private Long id;

    private Long userId;

    private String userName;

    private Long sectionId;

    private String sectionName;

    private Long trainerId;

    private String trainerName;

    private String status;
}
