package ru.sfedu.ictis.sports_sections.dto.response;

import lombok.Getter;
import lombok.Setter;
import ru.sfedu.ictis.sports_sections.dto.response.common.BaseSuccessResponse;

@Setter
@Getter
public class CreateSectionResponse extends BaseSuccessResponse {
    private Long id;

    public CreateSectionResponse(Long id) {
        super(0);
        this.id = id;
    }
}
