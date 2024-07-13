package com.koview.koview_server.member.domain.dto;

import com.koview.koview_server.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SignupResponseDTO {
    private final Long id;
    private final String email;
    private final String nickname;
    private final int age;

    /* Entity -> DTO */
    public SignupResponseDTO(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.age = member.getAge();
    }
}
