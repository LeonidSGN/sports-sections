package ru.sfedu.ictis.sports_sections.exception;

public class CustomException extends RuntimeException {
    final ErrorCodes errorCodes;

    public CustomException(ErrorCodes errorCodes) {
        this.errorCodes = errorCodes;
    }
}
