package com.koview.koview_server.mypage.service;

import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.review.domain.dto.ReviewResponseDTO;
import org.springframework.data.domain.Pageable;

public interface MypageService {
    LimitedReviewResponseDTO.ReviewSlice findAllByMemberWithLimitedImages(Pageable pageable);
    ReviewResponseDTO.ReviewSlice findAllByMember(Pageable pageable, Long clickedReviewId);
    void deleteMyReview(Long reviewId);
    void deleteMyReviewList(ReviewRequestDTO.ReviewIdListDTO reviewIdListDTO);
}
