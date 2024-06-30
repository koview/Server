package com.koview.koview_server.config.apiPayload.code;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorReasonDto {

    private HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;
    public boolean getIsSuccess(){return isSuccess;}
}
