package com.koview.koview_server.global.common.image;

import com.koview.koview_server.imageTest.domain.ImagePath;
import com.koview.koview_server.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ImageResponseDTO {
    private Long imageId;
    private String url;

    public ImageResponseDTO(ImagePath imagePath) {
        this.imageId = imagePath.getId();
        this.url = imagePath.getUrl();
    }
}
