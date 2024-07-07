package com.koview.koview_server.member.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequestDTO {

    @NotEmpty
    private final String email;

    @NotEmpty
    private final String loginPw;

    @JsonCreator
    public LoginRequestDTO(@JsonProperty("email") String email, @JsonProperty("loginPw") String loginPw) {
        this.email = email;
        this.loginPw = loginPw;
    }
}