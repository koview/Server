package com.koview.koview_server.review.domain.dto;

import java.util.List;

import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.mypage.domain.dto.ProfileResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
        private ProfileResponseDTO profileInfo;
        private List<ImageResponseDTO> imageList;
        private Long totalCommentCount;
        private Long totalLikesCount;
        private Boolean isLiked;
        private String createdAt;
        private String updatedAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ReviewSlice {

        private List<Single> reviewList;
        private Integer getNumber;
        private Boolean hasPrevious;
        private Boolean hasNext;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ReviewPaging {

        private List<Single> reviewList;
        private Integer getNumber;
        private Boolean hasPrevious;
        private Boolean hasNext;
        private Integer getTotalPages;
        private Long getTotalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }
}
