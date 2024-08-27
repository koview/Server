package com.koview.koview_server.api.common.apiPayload.exception;

import com.koview.koview_server.api.common.apiPayload.code.BaseErrorCode;
import com.koview.koview_server.api.common.apiPayload.code.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException{
    private BaseErrorCode code;
    public ErrorReasonDto getErrorReason(){
        return this.code.getReason();
    }
    public ErrorReasonDto getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}
