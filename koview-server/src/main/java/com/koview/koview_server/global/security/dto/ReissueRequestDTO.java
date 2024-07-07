package com.koview.koview_server.global.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReissueRequestDTO {
    private String accessToken;
    private String refreshToken;
}
