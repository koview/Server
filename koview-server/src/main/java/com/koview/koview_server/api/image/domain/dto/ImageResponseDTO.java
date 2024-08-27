package com.koview.koview_server.api.image.domain.dto;

import com.koview.koview_server.api.image.domain.QueryImage;
import com.koview.koview_server.api.image.domain.ReviewImage;
import com.koview.koview_server.api.image.domain.ProfileImage;
import com.koview.koview_server.api.image.domain.ProductImage;

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
    public ImageResponseDTO(ProfileImage profileImage) {
        this.imageId = profileImage.getId();
        this.url = profileImage.getUrl();
    }
    public ImageResponseDTO(QueryImage queryImage) {
        this.imageId = queryImage.getId();
        this.url = queryImage.getUrl();
    }
}
