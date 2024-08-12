package com.koview.koview_server.review.domain.dto;

import com.koview.koview_server.global.common.image.ImageResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDate;
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
        private ImageResponseDTO profileImage;
        private List<ImageResponseDTO> imageList;
        private Long totalCommentCount;
        private Long totalLikesCount;
        private Boolean isCurrentMemberLiked;
        private String createdAt;
        private String updatedAt;
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
