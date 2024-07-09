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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "Member", description = "Member API")
public class MemberController {

    private final MemberServiceImpl memberServiceImpl;

    @GetMapping("/confirmEmail/{email}")
    @Operation(description = "회원가입 시 이메일 중복 확인")
    public ApiResult<?> confirmEmail(@PathVariable("email") String email) {
        if (memberServiceImpl.confirmEmail(email)) {
            throw new MemberException(ErrorStatus.MEMBER_EMAIL_ALREADY_EXISTS);
        }
        return ApiResult.onSuccess();
    }

    @GetMapping("/confirmName/{name}")
    @Operation(description = "회원가입 시 닉네임 중복 확인")
    public ApiResult<?> confirmName(@PathVariable("name") String name) {
        if (memberServiceImpl.confirmName(name)) {
            throw new MemberException(ErrorStatus.MEMBER_NAME_ALREADY_EXISTS);
        }
        return ApiResult.onSuccess();
    }

    @PostMapping("/signup")
    @Operation(description = "회원가입")
    public ApiResult<SignupResponseDTO> signup(@RequestBody SignupRequestDTO requestDTO) {
        SignupResponseDTO responseDTO = memberServiceImpl.createMember(requestDTO);
        return ApiResult.onSuccess(responseDTO);
    }

    @PostMapping("/signin")
    @Operation(description = "로그인")
    public ApiResult<JwtTokenDTO> signIn(@RequestBody LoginRequestDTO requestDTO) {
        JwtTokenDTO jwtTokenDTO = memberServiceImpl.signIn(requestDTO);

        return ApiResult.onSuccess(jwtTokenDTO);
    }

    @PostMapping("/reissue")
    @Operation(description = "Access 토큰 만료 시 재발급")
    public ApiResult<JwtTokenDTO> reissue(@RequestBody ReissueRequestDTO requestDTO) {
        return ApiResult.onSuccess(memberServiceImpl.reissue(requestDTO));
    }
}