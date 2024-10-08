package com.koview.koview_server.api.user.review.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.koview.koview_server.api.common.purchaseLink.domain.dto.PurchaseLinkRequestDTO;
import com.koview.koview_server.api.user.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Review Create Request")
public class ReviewRequestDTO {

    @NotBlank
    @Schema(description = "리뷰 내용", example = "리뷰 내용 테스트")
    private String content;

    @Schema(description = "이미지 ID 리스트", example = "[1, 2, 3]")
    private List<Long> imagePathIdList;

    private List<PurchaseLinkRequestDTO> purchaseLinkList;

    public Review toEntity() {
        return Review.builder()
                .content(content)
                .build();
    }

    @Builder
    @Getter
    public static class ReviewIdListDTO {

        @Schema(description = "리뷰 ID 리스트", example = "[1, 2, 3]")
        private List<Long> reviewIdList;

        @JsonCreator
        public ReviewIdListDTO(@JsonProperty("reviewIdList") List<Long> reviewIdList) {
            this.reviewIdList = reviewIdList;
        }
    }
}
