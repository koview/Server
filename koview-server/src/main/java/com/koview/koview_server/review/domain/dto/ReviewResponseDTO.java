package com.koview.koview_server.review.domain.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.mypage.domain.dto.ProfileResponseDTO;
import com.koview.koview_server.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import com.koview.koview_server.review.domain.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ReviewResponseDTO {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Single {

        private Long reviewId;
        private String content;
        private ProfileResponseDTO profileInfo;
        private List<ImageResponseDTO> imageList;
        private List<PurchaseLinkResponseDTO> purchaseLinkList;
        private Long totalCommentCount;
        private Long totalLikeCount;
        private Boolean isLiked;
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
        private ProfileResponseDTO profileInfo;
        private List<ImageResponseDTO> imageList;
        private Long totalCommentCount;
        private Long totalLikeCount;
        private Boolean isLiked;
        private String createdAt;
        private String updatedAt;


        public toReviewDTO(Review review, Boolean isLiked) {
            this.reviewId = review.getId();
            this.content = review.getContent();
            this.profileInfo = review.getMember() != null ?
                new ProfileResponseDTO(review.getMember().getProfileImage().getId(),
                    review.getMember().getProfileImage().getUrl(),
                    review.getMember().getId(),
                    review.getMember().getNickname()) : null;
            this.imageList = review.getReviewImageList() != null ?
                    review.getReviewImageList().stream()
                            .distinct()
                            .map(ImageResponseDTO::new)
                            .collect(Collectors.toList()) : null;
            this.totalCommentCount = review.getCommentList() != null ? (long) review.getCommentList().size() : 0L;
            this.totalLikeCount = review.getTotalLikesCount();
            this.isLiked = isLiked;
            this.createdAt = review.getCreatedDate().format(formatter);
            this.updatedAt = review.getLastModifiedDate().format(formatter);

        }
    }
}
