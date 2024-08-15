package com.koview.koview_server.member.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProfileResponseDTO {
    private final Long memberId;
    private final String nickname;
    private final String profileImage;

    // TODO: 리뷰 응답에 profileDTO 추가
}
