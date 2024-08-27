package com.koview.koview_server.api.common.apiPayload.exception;

import com.koview.koview_server.api.common.apiPayload.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class MemberException extends GeneralException {
    public MemberException(BaseErrorCode code) {
        super(code);
    }
}

