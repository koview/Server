package com.koview.koview_server.member.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Login Request")
public class LoginRequestDTO {

    @NotEmpty
    @Schema(description = "회원 이메일", example = "test1234@gmail.com")
    private final String email;

    @NotEmpty
    @Schema(description = "회원 비밀번호", example = "test1234")
    private final String loginPw;

    @JsonCreator
    public LoginRequestDTO(@JsonProperty("email") String email, @JsonProperty("loginPw") String loginPw) {
        this.email = email;
        this.loginPw = loginPw;
    }
}