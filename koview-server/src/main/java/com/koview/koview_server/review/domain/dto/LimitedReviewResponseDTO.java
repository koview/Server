package com.koview.koview_server.review.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class LimitedReviewResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Single {

        private Long reviewId;
        private String content;
        private String writer;
        private List<Long> imagePathIdList;
        private Long totalCommentCount;
        private Long totalLikesCount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ReviewSlice {

        private List<Single> reviewList;
        private int getNumber;
        private boolean hasPrevious;
        private boolean hasNext;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ReviewPaging {

        private List<Single> reviewList;
        private int getNumber;
        private boolean hasPrevious;
        private boolean hasNext;
        private int getTotalPages;
        private long getTotalElements;
        private boolean isFirst;
        private boolean isLast;
    }
}
