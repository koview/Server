package com.koview.koview_server.global.common.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ImageResponseDTO {
    private Long imageId;
    private String url;

    /**
     * Todo: Image abstract class를 만들어서 모든 이미지 Entity->DTO로 받는 방법 추가해보기
     * 지금은 임시로 converter 에서 구현
     */

}
