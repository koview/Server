package com.koview.koview_server.api.user.review.domain.dto;

import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.image.domain.ProfileImage;
import com.koview.koview_server.api.image.domain.dto.ImageResponseDTO;
import com.koview.koview_server.api.common.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import com.koview.koview_server.api.user.review.domain.Review;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.koview.koview_server.api.common.ProfileResponseDTO;

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
            Long likeCount = review.getTotalLikesCount();

            ProfileResponseDTO profileInfo = getProfileInfo(review.getMember());

            List<ImageResponseDTO> imageList = review.getReviewImageList().stream()
                    .map(ImageResponseDTO::new)
                    .collect(Collectors.toList());

            this.reviewId = review.getId();
            this.content = review.getContent();
            this.profileInfo = profileInfo;
            this.imageList = review.getReviewImageList() != null ? imageList : null;
            this.totalCommentCount = review.getCommentList() != null ? (long) review.getCommentList().size() : 0L;
            this.totalLikeCount = likeCount != null ? likeCount : 0L;
            this.isLiked = isLiked;
            this.createdAt = review.getCreatedDate().format(formatter);
            this.updatedAt = review.getLastModifiedDate().format(formatter);

        }
        private static ProfileResponseDTO getProfileInfo(Member member){
            ProfileImage profileImage = member.getProfileImage();
            boolean isProfileImage = profileImage != null;

            return ProfileResponseDTO.builder()
                    .imageId(isProfileImage ? profileImage.getId() : null)
                    .imageUrl(isProfileImage ? profileImage.getUrl() : null)
                    .memberId(member.getId())
                    .memberNickname(member.getNickname())
                    .build();
        }
    }
}
