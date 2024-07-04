package com.koview.koview_server.global.apiPayload.code;

public interface BaseErrorCode {
    public ErrorReasonDto getReason();
    public ErrorReasonDto getReasonHttpStatus();
    public String getMessage();

    default String getCode() {
        return getReasonHttpStatus().getCode();
    }

    default org.springframework.http.HttpStatus getHttpStatus() {
        return getReasonHttpStatus().getHttpStatus();
    }
}
