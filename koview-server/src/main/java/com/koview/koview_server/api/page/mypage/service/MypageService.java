package com.koview.koview_server.api.page.mypage.service;

import com.koview.koview_server.api.common.ProfileResponseDTO;
import com.koview.koview_server.api.user.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.api.user.review.domain.dto.ReviewRequestDTO;
import org.springframework.data.domain.Pageable;

public interface MypageService {
    LimitedReviewResponseDTO.ReviewSlice findAllByMemberWithLimitedImages(Pageable pageable);
    void deleteMyReview(Long reviewId);
    void deleteMyReviewList(ReviewRequestDTO.ReviewIdListDTO reviewIdListDTO);
    ProfileResponseDTO findMyProfile();
}
