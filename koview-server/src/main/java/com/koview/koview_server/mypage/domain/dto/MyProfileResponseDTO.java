package com.koview.koview_server.mypage.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyProfileResponseDTO {

    private Long imageId;
    private String url;
    private String nickname;
}
