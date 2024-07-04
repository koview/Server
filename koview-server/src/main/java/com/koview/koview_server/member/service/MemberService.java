package com.koview.koview_server.member.service;

import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.domain.dto.SignupRequestDTO;
import com.koview.koview_server.member.domain.dto.SignupResponseDTO;

import java.util.Optional;

public interface MemberService {

    boolean confirmEmail(String email);
    boolean confirmName(String name);
    SignupResponseDTO createMember(SignupRequestDTO signupRequestDTO);
    Optional<Member> findByEmail(String email);
}
