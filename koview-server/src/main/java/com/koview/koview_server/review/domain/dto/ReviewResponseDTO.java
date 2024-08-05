package com.koview.koview_server.review.domain.dto;

import com.koview.koview_server.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import com.koview.koview_server.image.domain.ReviewImage;
import com.koview.koview_server.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewResponseDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class Single {

        private Long reviewId;
        private String content;
        private String writer;
        private List<Long> imagePathIdList;
        private List<PurchaseLinkResponseDTO> purchaseLinkList;
        private Long totalCommentCount;
        private Long totalLikesCount;
        private LocalDate createdAt;
        private LocalDate updatedAt;
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
        private List<Long> imagePathIdList;
        private Long totalCommentCount;
        private Long totalLikesCount;
        private LocalDate createdAt;
        private LocalDate updatedAt;


        public toReviewDTO(Review review) {
            this.reviewId = review.getId();
            this.content = review.getContent();
            this.writer = review.getMember().getNickname();
            this.imagePathIdList = review.getReviewImageList() != null ?
                    review.getReviewImageList().stream()
                            .map(ReviewImage::getId)
                            .collect(Collectors.toList()) : null;
            this.totalCommentCount = review.getCommentList() != null ? (long) review.getCommentList().size() : 0L;
            this.totalLikesCount = review.getTotalLikesCount();
            this.createdAt = review.getCreatedDate().toLocalDate();
            this.updatedAt = review.getLastModifiedDate().toLocalDate();

        }
    }
}
