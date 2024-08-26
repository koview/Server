package com.koview.koview_server.mypage.service;

import com.koview.koview_server.mypage.domain.dto.ProfileResponseDTO;
import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;

import org.springframework.data.domain.Pageable;

public interface MypageService {
    LimitedReviewResponseDTO.ReviewSlice findAllByMemberWithLimitedImages(Pageable pageable);
    void deleteMyReview(Long reviewId);
    void deleteMyReviewList(ReviewRequestDTO.ReviewIdListDTO reviewIdListDTO);
    ProfileResponseDTO findMyProfile();
}
