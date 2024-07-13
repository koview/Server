package com.koview.koview_server.review.service;

import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.review.domain.dto.ReviewResponseDTO;

public interface ReviewService {

    ReviewResponseDTO createReview(ReviewRequestDTO requestDTO);
}
