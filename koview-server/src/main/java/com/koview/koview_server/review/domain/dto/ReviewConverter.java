package com.koview.koview_server.review.domain.dto;

import com.koview.koview_server.imageTest.domain.ImagePath;
import com.koview.koview_server.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import com.koview.koview_server.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewConverter {

    public static LimitedReviewResponseDTO.Single toLimitedSingleDto(Review review){
        return LimitedReviewResponseDTO.Single.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .writer(review.getMember().getNickname())
                .imagePathIdList(review.getImagePathList() != null ?
                        review.getImagePathList().stream()
                                .limit(2)
                                .map(ImagePath::getId)
                                .collect(Collectors.toList()) : null)
                .totalCommentCount(review.getCommentList().size())
                .totalLikesCount(review.getTotalLikesCount() != null ? review.getTotalLikesCount() : 0L)
                .build();
    }
    public static LimitedReviewResponseDTO.ReviewSlice toLimitedSliceDto(Slice<Review> reviewSlice,
                                                           List<LimitedReviewResponseDTO.Single> reviewList){
        return LimitedReviewResponseDTO.ReviewSlice.builder()
                .reviewList(reviewList)
                .getNumber(reviewSlice.getNumber())
                .hasNext(reviewSlice.hasNext())
                .hasPrevious(reviewSlice.hasPrevious())
                .build();
    }

    public static ReviewResponseDTO.Single toSingleDTO(Review review, List<PurchaseLinkResponseDTO> purchaseLink){
        return ReviewResponseDTO.Single.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .writer(review.getMember().getNickname())
                .imagePathIdList(review.getImagePathList() != null ?
                        review.getImagePathList().stream()
                                .map(ImagePath::getId)
                                .collect(Collectors.toList()) : null)
                .totalCommentCount(review.getCommentList().size())
                .totalLikesCount(review.getTotalLikesCount() != null ? review.getTotalLikesCount() : 0L)
                .purchaseLinkList(purchaseLink)
                .build();
    }
    public static ReviewResponseDTO.ReviewSlice toSliceDTO(Slice<Review> reviewSlice,
                                                             List<ReviewResponseDTO.Single> reviewList){
        return ReviewResponseDTO.ReviewSlice.builder()
                .reviewList(reviewList)
                .getNumber(reviewSlice.getNumber())
                .hasNext(reviewSlice.hasNext())
                .hasPrevious(reviewSlice.hasPrevious())
                .build();
    }

    public static LimitedReviewResponseDTO.ReviewPaging toLimitedPagingDTO(Page<Review> reviewPage,
                                                             List<LimitedReviewResponseDTO.Single> reviewList){
        return LimitedReviewResponseDTO.ReviewPaging.builder()
                .reviewList(reviewList)
                .getTotalPages(reviewPage.getTotalPages())
                .getNumber(reviewPage.getNumber())
                .getTotalElements(reviewPage.getTotalElements())
                .isFirst(reviewPage.isFirst())
                .isLast(reviewPage.isLast())
                .hasNext(reviewPage.hasNext())
                .hasPrevious(reviewPage.hasPrevious())
                .build();
    }
}
