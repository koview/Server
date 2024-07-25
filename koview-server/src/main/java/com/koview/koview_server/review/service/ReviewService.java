package com.koview.koview_server.review.service;

import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.review.domain.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {

    ReviewResponseDTO createReview(ReviewRequestDTO requestDTO);
    void deleteReview(Long reviewId);
    void deleteReviewList(ReviewRequestDTO.ReviewIdListDTO reviewIdListDTO);
    List<LimitedReviewResponseDTO> findAllWithLimitedImages();
    List<ReviewResponseDTO> findAll();
}
