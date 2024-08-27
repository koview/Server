package com.koview.koview_server.api.common.apiPayload.exception;

import com.koview.koview_server.api.common.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class CustomAuthenticationException extends AuthenticationException {

    private final BaseErrorCode errorCode;
    public CustomAuthenticationException(BaseErrorCode errorCode, Throwable e) {
        super(errorCode.getMessage(),e);
        this.errorCode = errorCode;
    }
}
