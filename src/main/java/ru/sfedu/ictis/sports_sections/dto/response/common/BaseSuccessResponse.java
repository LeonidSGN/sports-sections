package ru.sfedu.ictis.sports_sections.dto.response.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseSuccessResponse {
    private boolean success = true;

    private int statusCode;

    private List<Integer> codes;

    public BaseSuccessResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    public BaseSuccessResponse(int statusCode, List<Integer> codes) {
        this.statusCode = statusCode;
        this.codes = codes;
    }
}
