package com.koview.koview_server.review.domain.dto;

import com.koview.koview_server.imageTest.domain.ImagePath;
import com.koview.koview_server.review.domain.Review;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewConverter {

    public static LimitedReviewResponseDTO.Single toSingleDto(Review review){
        return LimitedReviewResponseDTO.Single.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .writer(review.getMember().getNickname())
                .imagePathIdList(review.getImagePathList() != null ?
                        review.getImagePathList().stream()
                                .limit(2)
                                .map(ImagePath::getId)
                                .collect(Collectors.toList()) : null)
                .build();
    }
    public static LimitedReviewResponseDTO.ReviewSlice toSliceDto(Slice<Review> reviewSlice,
                                                           List<LimitedReviewResponseDTO.Single> reviewList){
        return LimitedReviewResponseDTO.ReviewSlice.builder()
                .reviewList(reviewList)
                .getNumber(reviewSlice.getNumber())
                .hasNext(reviewSlice.hasNext())
                .hasPrevious(reviewSlice.hasPrevious())
                .build();
    }

    public static ReviewResponseDTO.Single toSingleDTO(Review review){
        return ReviewResponseDTO.Single.builder()
                .reviewId(review.getId())
                .content(review.getContent())
                .writer(review.getMember().getNickname())
                .imagePathIdList(review.getImagePathList() != null ?
                        review.getImagePathList().stream()
                                .map(ImagePath::getId)
                                .collect(Collectors.toList()) : null)
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
}
