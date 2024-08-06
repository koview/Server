package com.koview.koview_server.review.domain.dto;

import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import com.koview.koview_server.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewResponseDTO {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Single {

        private Long reviewId;
        private String content;
        private String writer;
        private List<ImageResponseDTO> imageList;
        private List<PurchaseLinkResponseDTO> purchaseLinkList;
        private Long totalCommentCount;
        private Long totalLikesCount;
        private String createdAt;
        private String updatedAt;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class ReviewSlice {

        private List<ReviewResponseDTO.Single> reviewList;
        private Integer getNumber;
        private Boolean hasPrevious;
        private Boolean hasNext;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class toReviewDTO {
        private Long reviewId;
        private String content;
        private String writer;
        private List<ImageResponseDTO> imageList;
        private Long totalCommentCount;
        private Long totalLikesCount;
        private String createdAt;
        private String updatedAt;


        public toReviewDTO(Review review) {
            this.reviewId = review.getId();
            this.content = review.getContent();
            this.writer = review.getMember().getNickname();
            this.imageList = review.getReviewImageList() != null ?
                    review.getReviewImageList().stream()
                            .map(ImageResponseDTO::new)
                            .collect(Collectors.toList()) : null;
            this.totalCommentCount = review.getCommentList() != null ? (long) review.getCommentList().size() : 0L;
            this.totalLikesCount = review.getTotalLikesCount();
            this.createdAt = review.getCreatedDate().format(formatter);
            this.updatedAt = review.getLastModifiedDate().format(formatter);

        }
    }
}
