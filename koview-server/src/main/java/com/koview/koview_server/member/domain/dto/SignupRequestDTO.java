package com.koview.koview_server.member.domain.dto;

import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.domain.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "Sign-up Request")
public class SignupRequestDTO {

    @NotBlank
    @Size(min = 6,max = 10, message ="아이디는 6자 이상 10자 이하만 가능합니다.")
    @Pattern(regexp = "^[a-z0-9@._-]*$", message = "작성자명은 알파벳 소문자, 숫자만 사용 가능합니다.")
    @Schema(description = "회원 이메일", example = "test1234@gmail.com")
    private String email;

    @NotBlank
    @Size(min = 8,max = 12, message ="비밀번호는 8자 이상 12자 이하만 가능합니다.")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "작성자명은 알파벳 대문자, 소문자, 숫자만 사용 가능합니다.")
    @Schema(description = "회원 비밀번호", example = "test1234")
    private String loginPw;

    @NotBlank
    @Schema(description = "회원 닉네임", example = "홍길동")
    private String nickname;

    @Schema(description = "회원 나이", example = "20")
    private int age;

    @Schema(description = "사용자 선호 쇼핑몰 ID 리스트", example = "[1, 2, 3]")
    private List<Long> shopId;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .loginPw(loginPw)
                .nickname(nickname)
                .age(age)
                .memberLikedShopList(new ArrayList<>())
                .build();
    }
}
