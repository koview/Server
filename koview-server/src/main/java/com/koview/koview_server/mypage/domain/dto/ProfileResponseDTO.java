package com.koview.koview_server.mypage.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProfileResponseDTO {

    private Long imageId;
    private String imageUrl;
    private Long memberId;
    private String memberNickname;
}
