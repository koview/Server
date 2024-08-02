package com.koview.koview_server.global.common.image;

import com.koview.koview_server.imageTest.domain.ReviewImage;
import com.koview.koview_server.product.domain.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ImageResponseDTO {
    private Long imageId;
    private String url;

    public ImageResponseDTO(ReviewImage reviewImage) {
        this.imageId = reviewImage.getId();
        this.url = reviewImage.getUrl();
    }
    public ImageResponseDTO(ProductImage reviewImage) {
        this.imageId = reviewImage.getId();
        this.url = reviewImage.getUrl();
    }
}
