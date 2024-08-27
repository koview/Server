package com.koview.koview_server.api.common.apiPayload.exception;

import com.koview.koview_server.api.common.apiPayload.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class ReviewException extends RuntimeException {
    private final BaseErrorCode errorCode;

    public ReviewException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
