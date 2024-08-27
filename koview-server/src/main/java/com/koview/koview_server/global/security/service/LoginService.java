package com.koview.koview_server.global.security.service;

import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.auth.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberServiceImpl memberService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));
        return User.builder()
                .username(member.getEmail())
                .password(member.getLoginPw())
                .roles(member.getRole().name())
                .build();
    }
}
