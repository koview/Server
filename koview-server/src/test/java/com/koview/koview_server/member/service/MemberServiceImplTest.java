package com.koview.koview_server.member.service;

import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.domain.RoleType;
import com.koview.koview_server.member.domain.dto.SignupRequestDTO;
import com.koview.koview_server.member.domain.dto.SignupResponseDTO;
import com.koview.koview_server.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberServiceImpl memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMember() {
        // Given
        SignupRequestDTO requestDTO = SignupRequestDTO.builder()
                .email("test@example.com")
                .loginPw("password")
                .verifiedPw("password")
                .name("Test User")
                .age(30)
                .roleType(RoleType.USER)
                .build();

        Member savedMember = Member.builder()
                .id(1L)
                .email("test@example.com")
                .loginPw("password")
                .name("Test User")
                .age(30)
                .role(RoleType.USER)
                .build();

        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(memberRepository.save(any())).thenReturn(savedMember);

        // When
        SignupResponseDTO responseDTO = memberService.createMember(requestDTO);

        // Then
        assertNotNull(responseDTO);
        assertEquals(savedMember.getEmail(), responseDTO.getEmail());
        assertEquals(savedMember.getName(), responseDTO.getName());
        assertEquals(savedMember.getAge(), responseDTO.getAge());
        verify(memberRepository, times(1)).save(any());
    }

    @Test
    void testFindByEmail() {
        // Given
        String email = "test@example.com";
        Member member = Member.builder()
                .id(1L)
                .email(email)
                .loginPw("password")
                .name("Test User")
                .age(30)
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
