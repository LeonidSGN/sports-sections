package ru.sfedu.ictis.sports_sections.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.sfedu.ictis.sports_sections.exception.ValidationConstants;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class SessionDtoRequest {
    @NotNull(message = ValidationConstants.SECTION_ID_NULL)
    @Positive
    private Long sectionId;

    @NotBlank(message = ValidationConstants.STATUS_NULL)
    @Size(min = 1, max = 100, message = ValidationConstants.STATUS_SIZE_NOT_VALID)
    private String status;

    @NotNull(message = ValidationConstants.DATE_NULL)
    @Future(message = ValidationConstants.DATE_NOT_FUTURE)
    private LocalDateTime startDate;

    @NotNull(message = ValidationConstants.DATE_NULL)
    @Future(message = ValidationConstants.DATE_NOT_FUTURE)
    private LocalDateTime endDate;
}
