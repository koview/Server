package com.koview.koview_server.member.service;

import com.koview.koview_server.global.security.dto.JwtTokenDTO;
import com.koview.koview_server.global.security.dto.ReissueRequestDTO;
import com.koview.koview_server.global.security.service.JwtTokenProvider;
import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.domain.RefreshToken;
import com.koview.koview_server.member.domain.RoleType;
import com.koview.koview_server.member.domain.dto.LoginRequestDTO;
import com.koview.koview_server.member.domain.dto.SignupRequestDTO;
import com.koview.koview_server.member.domain.dto.SignupResponseDTO;
import com.koview.koview_server.member.repository.MemberRepository;
import com.koview.koview_server.member.repository.RefreshTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private MemberServiceImpl memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원가입")
    void signUp() {
        // Given
        SignupRequestDTO requestDTO = SignupRequestDTO.builder()
                .email("test@example.com")
                .loginPw("password")
                .verifiedPw("password")
                .nickname("Test User")
                .age(20)
                .roleType(RoleType.USER)
                .build();

        Member savedMember = Member.builder()
                .id(1L)
                .email("test@example.com")
                .loginPw("password")
                .nickname("Test User")
                .age(20)
                .role(RoleType.USER)
                .build();

        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(memberRepository.save(any())).thenReturn(savedMember);

        // When
        SignupResponseDTO responseDTO = memberService.createMember(requestDTO);

        // Then
        assertNotNull(responseDTO);
        assertEquals(savedMember.getEmail(), responseDTO.getEmail());
        assertEquals(savedMember.getNickname(), responseDTO.getNickname());
        assertEquals(savedMember.getAge(), responseDTO.getAge());
        verify(memberRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("로그인")
    void signIn() {
        // Given
        LoginRequestDTO requestDTO = LoginRequestDTO.builder()
                .email("test@example.com")
                .loginPw("password")
                .build();

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getLoginPw());

        when(authenticationManagerBuilder.getObject()).thenReturn(mock(AuthenticationManager.class));
        when(authenticationManagerBuilder.getObject().authenticate(authenticationToken)).thenReturn(authentication);

        JwtTokenDTO jwtTokenDTO = JwtTokenDTO.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();

        when(jwtTokenProvider.generateToken(authentication)).thenReturn(jwtTokenDTO);

        // When
        JwtTokenDTO result = memberService.signIn(requestDTO);

        // Then
        assertNotNull(result);
        assertEquals(jwtTokenDTO.getAccessToken(), result.getAccessToken());
        assertEquals(jwtTokenDTO.getRefreshToken(), result.getRefreshToken());
        verify(refreshTokenRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("토큰 재발급")
    void reissue() {
        // Given
        ReissueRequestDTO requestDTO = ReissueRequestDTO.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();

        when(jwtTokenProvider.validateToken(requestDTO.getRefreshToken())).thenReturn(true);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");

        when(jwtTokenProvider.getAuthentication(requestDTO.getAccessToken())).thenReturn(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(requestDTO.getRefreshToken())
                .build();

        when(refreshTokenRepository.findByKey(authentication.getName())).thenReturn(Optional.of(refreshToken));

        JwtTokenDTO newJwtTokenDTO = JwtTokenDTO.builder()
                .accessToken("newAccessToken")
                .refreshToken("newRefreshToken")
                .build();

        when(jwtTokenProvider.generateToken(authentication)).thenReturn(newJwtTokenDTO);

        // When
        JwtTokenDTO result = memberService.reissue(requestDTO);

        // Then
        assertNotNull(result);
        assertEquals(newJwtTokenDTO.getAccessToken(), result.getAccessToken());
        assertEquals(newJwtTokenDTO.getRefreshToken(), result.getRefreshToken());
        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
    }

    @Test
    @DisplayName("이메일로 멤버 찾기")
    void findByEmail() {
        // Given
        String email = "test@example.com";
        Member member = Member.builder()
                .id(1L)
                .email(email)
                .loginPw("password")
                .nickname("Test User")
                .age(20)
                .role(RoleType.USER)
                .build();
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));

        // When
        Optional<Member> foundMember = memberService.findByEmail(email);

        // Then
        assertTrue(foundMember.isPresent());
        assertEquals(member.getEmail(), foundMember.get().getEmail());
    }
}
