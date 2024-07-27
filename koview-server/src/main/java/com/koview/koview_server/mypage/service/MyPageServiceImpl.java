package com.koview.koview_server.mypage.service;

import com.koview.koview_server.global.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.global.apiPayload.exception.MemberException;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.repository.MemberRepository;
import com.koview.koview_server.review.domain.Review;
import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.review.domain.dto.ReviewConverter;
import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.review.domain.dto.ReviewResponseDTO;
import com.koview.koview_server.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageServiceImpl implements MypageService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public LimitedReviewResponseDTO.ReviewSlice findAllByMemberWithLimitedImages(Pageable pageable) {
        Member member = validateMember();
        Slice<Review> reviewSlice = reviewRepository.findAllByMember(member, pageable);

        return getLimitedImageReviewSlice(reviewSlice);
    }

    @Override
    public ReviewResponseDTO.ReviewSlice findAllByMember(Pageable pageable) {
        Member member = validateMember();
        Slice<Review> reviewSlice = reviewRepository.findAllByMember(member, pageable);

        return getReviewSlice(reviewSlice);
    }

    @Override
    public void deleteMyReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public void deleteMyReviewList(ReviewRequestDTO.ReviewIdListDTO reviewIdListDTO) {
        for (Long reviewId : reviewIdListDTO.getReviewIdList()) {
            reviewRepository.deleteById(reviewId);
        }
    }

    private Member validateMember() {
        return memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(
                () -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    private LimitedReviewResponseDTO.ReviewSlice getLimitedImageReviewSlice(Slice<Review> reviewSlice) {
        List<LimitedReviewResponseDTO.Single> reviewList = reviewSlice.stream().map(ReviewConverter::toSingleDto).toList();

        return ReviewConverter.toSliceDto(reviewSlice, reviewList);
    }

    private ReviewResponseDTO.ReviewSlice getReviewSlice(Slice<Review> reviewSlice) {
        List<ReviewResponseDTO.Single> reviewList = reviewSlice.stream().map(ReviewConverter::toSingleDTO).toList();

        return ReviewConverter.toSliceDTO(reviewSlice, reviewList);
    }
}
