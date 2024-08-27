package com.koview.koview_server.api.user.review.domain.dto;

import com.koview.koview_server.api.user.review.domain.Review;
import com.koview.koview_server.api.image.domain.dto.ImageResponseDTO;
import com.koview.koview_server.api.common.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewConverter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LimitedReviewResponseDTO.Single toLimitedSingleDto(Review review, Boolean isLiked){
        Long likeCount = review.getTotalLikesCount();
        return LimitedReviewResponseDTO.Single.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .writer(review.getMember().getNickname())
                .profileImage(review.getMember().getProfileImage() != null ?
                        new ImageResponseDTO(review.getMember().getProfileImage()) : null)
                .imageList(review.getReviewImageList() != null ?
                        review.getReviewImageList().stream()
                                .limit(2)
                                .map(ImageResponseDTO::new)
                                .collect(Collectors.toList()) : null)
                .totalCommentCount((long) review.getCommentList().size())
                .totalLikesCount(likeCount != null ? likeCount : 0L)
                .isLiked(isLiked) // null일때는 false
                .createdAt(review.getCreatedDate().format(formatter))
                .updatedAt(review.getLastModifiedDate().format(formatter))
                .build();
    }
    public static LimitedReviewResponseDTO.ReviewSlice toLimitedSliceDto(Slice<Review> reviewSlice,
                                                           List<LimitedReviewResponseDTO.Single> reviewList){
        return LimitedReviewResponseDTO.ReviewSlice.builder()
                .reviewList(reviewList)
                .getNumber(reviewSlice.getNumber()+1)
                .hasNext(reviewSlice.hasNext())
                .hasPrevious(reviewSlice.hasPrevious())
                .build();
    }

    public static ReviewResponseDTO.Single toSingleDTO(Review review, Boolean isLiked,
                                                       List<PurchaseLinkResponseDTO> purchaseLink){
        return ReviewResponseDTO.Single.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .writer(review.getMember().getNickname())
                .profileImage(review.getMember().getProfileImage() != null ?
                        new ImageResponseDTO(review.getMember().getProfileImage()) : null)
                .imageList(review.getReviewImageList() != null ?
                        review.getReviewImageList().stream()
                                .map(ImageResponseDTO::new)
                                .collect(Collectors.toList()) : null)
                .totalCommentCount((long) review.getCommentList().size())
                .totalLikeCount(review.getTotalLikesCount() != null ? review.getTotalLikesCount() : 0L)
                .purchaseLinkList(purchaseLink)
                .isLiked(isLiked)
                .createdAt(review.getCreatedDate().format(formatter))
                .updatedAt(review.getLastModifiedDate().format(formatter))
                .build();
    }
    public static ReviewResponseDTO.ReviewSlice toSliceDTO(Slice<Review> reviewSlice,
                                                             List<ReviewResponseDTO.Single> reviewList){
        return ReviewResponseDTO.ReviewSlice.builder()
                .reviewList(reviewList)
                .getNumber(reviewSlice.getNumber()+1)
                .hasNext(reviewSlice.hasNext())
                .hasPrevious(reviewSlice.hasPrevious())
                .build();
    }

    public static LimitedReviewResponseDTO.ReviewPaging toLimitedPagingDTO(Page<Review> reviewPage,
                                                             List<LimitedReviewResponseDTO.Single> reviewList){
        return LimitedReviewResponseDTO.ReviewPaging.builder()
                .reviewList(reviewList)
                .getTotalPages(reviewPage.getTotalPages())
                .getNumber(reviewPage.getNumber()+1)
                .getTotalElements(reviewPage.getTotalElements())
                .isFirst(reviewPage.isFirst())
                .isLast(reviewPage.isLast())
                .hasNext(reviewPage.hasNext())
                .hasPrevious(reviewPage.hasPrevious())
                .build();
    }
}
