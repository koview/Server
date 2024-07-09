package com.koview.koview_server.member.domain;

import com.koview.koview_server.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; // 아이디

    @Column(nullable = false)
    private String loginPw; // 비밀번호

    @Column(nullable = false)
    private String verifiedPw; // 비밀번호 확인

    @Column(nullable = false, unique = true)
    private String nickname; // 닉네임

    private int age; // 나이

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RoleType role; // 권한

    /* 패스워드 암호화 관련 */
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.loginPw = passwordEncoder.encode(loginPw);
    }

    /* 권한 부여 */
    public void addMemberAuthority() {
        this.role = RoleType.USER;
    }
}