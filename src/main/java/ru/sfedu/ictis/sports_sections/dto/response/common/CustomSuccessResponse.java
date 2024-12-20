package ru.sfedu.ictis.sports_sections.dto.response.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomSuccessResponse<T> extends BaseSuccessResponse {
    private T data;

    public CustomSuccessResponse() {
        super(0);
    }

    public CustomSuccessResponse(T data) {
        super(0);
        this.data = data;
    }

    public CustomSuccessResponse(boolean success, int statusCode, T data) {
        super(statusCode);
        this.data = data;
    }
}
