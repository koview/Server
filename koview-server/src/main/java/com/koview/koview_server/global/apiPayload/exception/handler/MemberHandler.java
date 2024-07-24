//package com.koview.koview_server.global.apiPayload.exception.handler;
//
//import com.koview.koview_server.global.apiPayload.ApiResult;
//import com.koview.koview_server.global.apiPayload.code.BaseErrorCode;
//import com.koview.koview_server.global.apiPayload.exception.MemberException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class MemberHandler {
//
//    @ExceptionHandler(MemberException.class)
//    public ResponseEntity<ApiResult<?>> handleMemberException(MemberException e) {
//        BaseErrorCode errorCode = e.getErrorCode();
//        return ResponseEntity.status(errorCode.getHttpStatus())
//                .body(ApiResult.onFailure(errorCode.getCode(), errorCode.getMessage(), null));
//    }
//}
