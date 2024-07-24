package com.koview.koview_server.global.apiPayload.exception;

import com.koview.koview_server.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class MemberException extends GeneralException {
    public MemberException(BaseErrorCode code) {
        super(code);
    }
}

