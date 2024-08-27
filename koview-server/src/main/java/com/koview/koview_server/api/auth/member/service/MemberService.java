package com.koview.koview_server.api.auth.member.service;

import com.koview.koview_server.global.security.dto.JwtTokenDTO;
import com.koview.koview_server.global.security.dto.ReissueRequestDTO;
import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.auth.member.domain.dto.LoginRequestDTO;
import com.koview.koview_server.api.auth.member.domain.dto.SignupRequestDTO;
import com.koview.koview_server.api.auth.member.domain.dto.SignupResponseDTO;

import java.util.Optional;

public interface MemberService {

    boolean confirmEmail(String email);
    boolean confirmNickname(String nickname);
    SignupResponseDTO createMember(SignupRequestDTO signupRequestDTO);
    JwtTokenDTO signIn(LoginRequestDTO requestDTO);
    Member save(Member member);
    JwtTokenDTO reissue(ReissueRequestDTO requestDTO);
    Optional<Member> findByEmail(String email);
}
