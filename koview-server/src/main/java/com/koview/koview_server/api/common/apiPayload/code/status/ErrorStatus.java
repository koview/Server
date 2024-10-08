package com.koview.koview_server.api.common.apiPayload.code.status;

import com.koview.koview_server.api.common.apiPayload.code.BaseErrorCode;
import com.koview.koview_server.api.common.apiPayload.code.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COMMON500","서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404","데이터를 찾지 못했습니다."),

    // JWT 응답
    INCORRECT_FORMAT_TOKEN(HttpStatus.BAD_REQUEST,"JWT_TOKEN4001" , "올바르지 않은 형식의 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST,"JWT_TOKEN4002" , "유효기간이 만료된 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST,"JWT_TOKEN4003" , "지원되지 않는 형식의 토큰입니다."),
    TOKEN_WAS_EMPTY(HttpStatus.BAD_REQUEST,"JWT_TOKEN4004" , "비어있거나 null인 토큰입니다."),
    NOT_AUTHORIZED(HttpStatus.BAD_REQUEST,"JWT_TOKEN4004" , "권한 정보가 없는 토큰입니다."),

    // 유저 응답
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "유저가 존재하지 않습니다."),
    MEMBER_EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "MEMBER4002", "이미 존재하는 이메일입니다."),
    MEMBER_NAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "MEMBER4003", "이미 존재하는 닉네임입니다."),
    MEMBER_PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "MEMBER4004", "비밀번호가 일치하지 않습니다."),
    NOT_MATCHING_MEMBER_TOKEN(HttpStatus.BAD_REQUEST, "MEMBER4005", "토큰과 로그인 된 멤버가 일치하지 않습니다."),
    MEMBER_ACCESS_TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST, "MEMBER4006", "만료된 Access Token 입니다. 재발급을 요청하세요."),
    MEMBER_REFRESH_TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST, "MEMBER4007", "만료된 Refresh Token 입니다. 재발급을 요청하세요."),
    MEMBER_WAS_SIGN_OUT(HttpStatus.BAD_REQUEST, "MEMBER4008", "로그아웃 된 사용자입니다."),

    // 리뷰 응답
    REVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, "REVIEW4001", "존재하지 않는 리뷰입니다."),

    // 댓글 응답
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMENT4001", "존재하지 않는 댓글입니다."),

    // 좋아요 응답
    LIKES_NOT_FOUND(HttpStatus.BAD_REQUEST, "LIKES4001", "좋아요가 등록되지 않았습니다."),
    LIKES_IS_ALREADY_CLICKED(HttpStatus.BAD_REQUEST, "LIKES4001", "이미 좋아요를 등록하신 리뷰입니다."),

    // 이미지 에러
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "IMAGE4001", "사진이 존재하지 않습니다."),

    // 카테고리 응답
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"CATEGORY4001", "카테고리가 존재하지 않습니다."),

    // 상점 응답
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND,"PRODUCT4001" , "해당 상품이 존재하지 않습니다."),

    // 질문 응답
    QUERY_NOT_FOUND(HttpStatus.NOT_FOUND,"QUERY4001" , "해당 질문이 존재하지 않습니다."),

    // 나도 공감 응답
    WITH_QUERY_NOT_FOUND(HttpStatus.NOT_FOUND,"WITH_QUERY4001" , "나도 공감이 등록되지 않았습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    @Override
    public ErrorReasonDto getReason(){
        return ErrorReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }
    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
