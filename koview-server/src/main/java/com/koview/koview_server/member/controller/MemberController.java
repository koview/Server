package com.koview.koview_server.member.controller;

import com.koview.koview_server.global.apiPayload.ApiResult;
import com.koview.koview_server.global.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.global.apiPayload.exception.MemberException;
import com.koview.koview_server.global.security.dto.JwtTokenDTO;
import com.koview.koview_server.global.security.dto.ReissueRequestDTO;
import com.koview.koview_server.member.domain.dto.LoginRequestDTO;
import com.koview.koview_server.member.domain.dto.SignupRequestDTO;
import com.koview.koview_server.member.domain.dto.SignupResponseDTO;
import com.koview.koview_server.member.service.MemberServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberServiceImpl memberServiceImpl;

    @GetMapping("/confirmEmail/{email}")
    public ApiResult<?> confirmEmail(@PathVariable("email") String email) {
        if (memberServiceImpl.confirmEmail(email)) {
            throw new MemberException(ErrorStatus.MEMBER_EMAIL_ALREADY_EXISTS);
        }
        return ApiResult.onSuccess();
    }

    @GetMapping("/confirmName/{name}")
    public ApiResult<?> confirmName(@PathVariable("name") String name) {
        if (memberServiceImpl.confirmName(name)) {
            throw new MemberException(ErrorStatus.MEMBER_NAME_ALREADY_EXISTS);
        }
        return ApiResult.onSuccess();
    }

    @PostMapping("/signup")
    public ApiResult<?> signup(@RequestBody SignupRequestDTO requestDTO) {
        SignupResponseDTO responseDTO = memberServiceImpl.createMember(requestDTO);
        return ApiResult.onSuccess(responseDTO);
    }

    @PostMapping("/signin")
    public ApiResult<?> signIn(@RequestBody LoginRequestDTO requestDTO) {
        JwtTokenDTO jwtTokenDTO = memberServiceImpl.signIn(requestDTO);

        return ApiResult.onSuccess(jwtTokenDTO);
    }

    @PostMapping("/reissue")
    public ApiResult<?> reissue(@RequestBody ReissueRequestDTO requestDTO) {
        return ApiResult.onSuccess(memberServiceImpl.reissue(requestDTO));
    }

//    @PostConstruct
    public void initMemberData() {
        SignupRequestDTO member1 = SignupRequestDTO.builder()
                .email("test@gmail.com")
                .loginPw("test1234")
                .verifiedPw("test1234")
                .name("이름")
                .age(20)
                .build();

        memberServiceImpl.createMember(member1);
    }
}