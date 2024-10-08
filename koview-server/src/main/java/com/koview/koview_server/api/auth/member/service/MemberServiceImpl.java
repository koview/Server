package com.koview.koview_server.api.auth.member.service;

import com.koview.koview_server.api.auth.member.domain.MemberLikedShop;
import com.koview.koview_server.api.auth.member.repository.MemberLikedShopRepository;
import com.koview.koview_server.api.auth.member.repository.MemberRepository;
import com.koview.koview_server.api.auth.member.repository.RefreshTokenRepository;
import com.koview.koview_server.api.common.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.api.common.apiPayload.exception.MemberException;
import com.koview.koview_server.global.security.dto.JwtTokenDTO;
import com.koview.koview_server.global.security.dto.ReissueRequestDTO;
import com.koview.koview_server.global.security.service.JwtTokenProvider;
import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.auth.member.domain.RefreshToken;
import com.koview.koview_server.api.auth.member.domain.dto.LoginRequestDTO;
import com.koview.koview_server.api.auth.member.domain.dto.SignupRequestDTO;
import com.koview.koview_server.api.auth.member.domain.dto.SignupResponseDTO;
import com.koview.koview_server.api.auth.shop.domain.Shop;
import com.koview.koview_server.api.auth.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberLikedShopRepository memberLikedShopRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ShopRepository shopRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean confirmEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public boolean confirmNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Override
    public SignupResponseDTO createMember(SignupRequestDTO signupRequestDTO) {
        checkDuplicateEmail(signupRequestDTO);
        checkDuplicateNickname(signupRequestDTO);

        Member member = signupRequestDTO.toEntity();
        member.addMemberAuthority();
        member.encodePassword(passwordEncoder);

        List<Shop> shops = shopRepository.findAllByIdIn(signupRequestDTO.getShopIdList());

        for(Shop shop: shops){
            memberLikedShopRepository.save(
                    MemberLikedShop.builder()
                    .member(member)
                    .shop(shop)
                    .build()
            ).linkMember(member);
        }
        save(member);

        return new SignupResponseDTO(member);
    }

    public void checkDuplicateEmail(SignupRequestDTO signupRequestDTO) {
        if (memberRepository.existsByEmail(signupRequestDTO.getEmail())) {
            throw new MemberException(ErrorStatus.MEMBER_EMAIL_ALREADY_EXISTS);
        }
    }

    public void checkDuplicateNickname(SignupRequestDTO signupRequestDTO) {
        if (memberRepository.existsByNickname(signupRequestDTO.getNickname())) {
            throw new MemberException(ErrorStatus.MEMBER_NAME_ALREADY_EXISTS);
        }
    }

    @Override
    public JwtTokenDTO signIn(LoginRequestDTO requestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getLoginPw());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtTokenDTO jwtTokenDTO = jwtTokenProvider.generateToken(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(jwtTokenDTO.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        return jwtTokenDTO;
    }

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public JwtTokenDTO reissue(ReissueRequestDTO requestDTO) {
        if(!jwtTokenProvider.validateToken(requestDTO.getRefreshToken())) {
            throw new MemberException(ErrorStatus.MEMBER_REFRESH_TOKEN_IS_EXPIRED);
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(requestDTO.getAccessToken());
        log.info("authentication.getName = {}", authentication.getName());
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_WAS_SIGN_OUT));

        if (!refreshToken.getValue().equals(requestDTO.getRefreshToken())) {
            throw new MemberException(ErrorStatus.NOT_MATCHING_MEMBER_TOKEN);
        }

        JwtTokenDTO tokenDto = jwtTokenProvider.generateToken(authentication);
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDto;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}