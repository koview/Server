package com.koview.koview_server.review.service;

import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.review.domain.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {

    ReviewResponseDTO createReview(ReviewRequestDTO requestDTO);
    void deleteReview(Long reviewId);
    void deleteReviewList(ReviewRequestDTO.ReviewIdListDTO reviewIdListDTO);
    List<ReviewResponseDTO> findAll();
    ReviewResponseDTO findById(Long reviewId);
    List<ReviewResponseDTO> findAllByMember();
    void deleteMyReview(Long reviewId);
}
