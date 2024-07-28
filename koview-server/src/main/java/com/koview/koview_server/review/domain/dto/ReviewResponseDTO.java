package com.koview.koview_server.review.domain.dto;

import com.koview.koview_server.imageTest.domain.ImagePath;
import com.koview.koview_server.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
        private long totalCommentCount;
        private long totalLikesCount;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class ReviewSlice {

        private List<ReviewResponseDTO.Single> reviewList;
        private int getNumber;
        private boolean hasPrevious;
        private boolean hasNext;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class toReviewDTO {
        private Long reviewId;
        private String content;
        private String writer;
        private List<Long> imagePathIdList;
        private long totalCommentCount;
        private long totalLikesCount;

        public toReviewDTO(Review review) {
            this.reviewId = review.getId();
            this.content = review.getContent();
            this.writer = review.getMember().getNickname();
            this.imagePathIdList = review.getImagePathList() != null ?
                    review.getImagePathList().stream()
                            .map(ImagePath::getId)
                            .collect(Collectors.toList()) : null;
            this.totalCommentCount = review.getCommentList().size();
            this.totalLikesCount = review.getTotalLikesCount();
        }
    }
}
