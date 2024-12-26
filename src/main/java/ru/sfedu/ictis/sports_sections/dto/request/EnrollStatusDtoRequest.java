package ru.sfedu.ictis.sports_sections.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sfedu.ictis.sports_sections.exception.ValidationConstants;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollStatusDtoRequest {
    @NotBlank(message = ValidationConstants.STATUS_NULL)
    @Size(min = 1, max = 100, message = ValidationConstants.STATUS_SIZE_NOT_VALID)
    private String status;
}
