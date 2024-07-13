package com.koview.koview_server.review.domain.dto;

import com.koview.koview_server.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "Review Create Request")
public class ReviewRequestDTO {

    @NotBlank
    private String content;

    public Review toEntity() {
        return Review.builder()
                .content(content)
                .build();
    }
}
