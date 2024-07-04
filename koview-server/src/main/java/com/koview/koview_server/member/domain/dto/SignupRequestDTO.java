package com.koview.koview_server.member.domain.dto;

import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.domain.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequestDTO {

    @NotBlank
    @Size(min = 6,max = 10, message ="아이디는 6자 이상 10자 이하만 가능합니다.")
    @Pattern(regexp = "^[a-z0-9@._-]*$", message = "작성자명은 알파벳 소문자, 숫자만 사용 가능합니다.")
    private String email;

    @NotBlank
    @Size(min = 8,max = 12, message ="비밀번호는 8자 이상 12자 이하만 가능합니다.")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "작성자명은 알파벳 대문자, 소문자, 숫자만 사용 가능합니다.")
    private String loginPw;

    @NotBlank
    private String verifiedPw;

    @NotBlank
    private String name;

    private int age;
    private RoleType roleType;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .loginPw(loginPw)
                .verifiedPw(verifiedPw)
                .name(name)
                .age(age)
                .role(RoleType.USER)
                .build();
    }
}
