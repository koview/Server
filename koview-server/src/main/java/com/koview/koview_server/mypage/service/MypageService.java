package com.koview.koview_server.mypage.service;

import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.review.domain.dto.ReviewResponseDTO;

import java.util.List;

public interface MypageService {
    List<LimitedReviewResponseDTO> findAllByMemberWithLimitedImages();
    List<ReviewResponseDTO> findAllByMember();
    void deleteMyReview(Long reviewId);
    void deleteMyReviewList(ReviewRequestDTO.ReviewIdListDTO reviewIdListDTO);
}
