package com.koview.koview_server.mypage.service;

import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.review.domain.dto.ReviewResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MypageService {
    Slice<LimitedReviewResponseDTO> findAllByMemberWithLimitedImages(Pageable pageable);
    Slice<ReviewResponseDTO> findAllByMember(Pageable pageable);
    void deleteMyReview(Long reviewId);
    void deleteMyReviewList(ReviewRequestDTO.ReviewIdListDTO reviewIdListDTO);
}
