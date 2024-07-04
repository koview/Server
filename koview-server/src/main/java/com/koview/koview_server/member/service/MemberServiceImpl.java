package com.koview.koview_server.member.service;

import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.domain.dto.SignupRequestDTO;
import com.koview.koview_server.member.domain.dto.SignupResponseDTO;
import com.koview.koview_server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean confirmEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public boolean confirmName(String name) {
        return memberRepository.existsByName(name);
    }

    @Override
    public SignupResponseDTO createMember(SignupRequestDTO signupRequestDTO) {
        Member member = signupRequestDTO.toEntity();
        member.addMemberAuthority();
        member.encodePassword(passwordEncoder);
        memberRepository.save(member);

        return new SignupResponseDTO(member);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}