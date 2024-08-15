package com.koview.koview_server.review.service;

import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.review.domain.dto.ReviewResponseDTO;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    ReviewResponseDTO.toReviewDTO createReview(ReviewRequestDTO requestDTO);
    void deleteReview(Long reviewId);
    void deleteReviewList(ReviewRequestDTO.ReviewIdListDTO reviewIdListDTO);
    ReviewResponseDTO.ReviewSlice findAll(Pageable pageable);
    ReviewResponseDTO.ReviewSlice findAllByMember(Pageable pageable, Long clickedReviewId, Long memberId);
    ReviewResponseDTO.ReviewSlice searchReviews(String keyword, Pageable pageable);
}
