package com.koview.koview_server.mypage.service;

import com.koview.koview_server.global.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.global.apiPayload.exception.MemberException;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.repository.MemberRepository;
import com.koview.koview_server.mypage.domain.ProfileImage;
import com.koview.koview_server.mypage.domain.dto.MyProfileResponseDTO;
import com.koview.koview_server.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import com.koview.koview_server.purchaseLink.repository.ReviewPurchaseLinkRepository;
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
    private final ReviewPurchaseLinkRepository reviewPurchaseLinkRepository;

    @Override
    public LimitedReviewResponseDTO.ReviewSlice findAllByMemberWithLimitedImages(Pageable pageable) {
        Member member = validateMember();
        Slice<Review> reviewSlice = reviewRepository.findAllByMember(member, pageable);

        return getLimitedImageReviewSlice(reviewSlice);
    }

    @Override
    public ReviewResponseDTO.ReviewSlice findAllByMember(Pageable pageable, Long clickedReviewId) {
        Member member = validateMember();
        Slice<Review> reviewSlice = reviewRepository.findAllByMemberWithClickedReviewFirst(member, clickedReviewId, pageable);

        return getReviewSlice(reviewSlice);
    }

    @Override
    public void deleteMyReview(Long reviewId) {
        reviewPurchaseLinkRepository.deleteByReviewId(reviewId);
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public void deleteMyReviewList(ReviewRequestDTO.ReviewIdListDTO reviewIdListDTO) {
        for (Long reviewId : reviewIdListDTO.getReviewIdList()) {
            reviewPurchaseLinkRepository.deleteByReviewId(reviewId);
            reviewRepository.deleteById(reviewId);
        }
    }

    @Override
    public MyProfileResponseDTO findMyProfile() {
        Member member = validateMember();
        ProfileImage profileImage = member.getProfileImage();

        if (profileImage == null)
            return MyProfileResponseDTO.builder()
                    .nickname(member.getNickname())
                    .build();

        return MyProfileResponseDTO.builder()
                .imageId(member.getProfileImage().getId())
                .url(member.getProfileImage().getUrl())
                .nickname(member.getNickname())
                .build();
    }

    private Member validateMember() {
        return memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(
                () -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    private LimitedReviewResponseDTO.ReviewSlice getLimitedImageReviewSlice(Slice<Review> reviewSlice) {
        List<LimitedReviewResponseDTO.Single> reviewList = reviewSlice.stream().map(ReviewConverter::toLimitedSingleDto).toList();

        return ReviewConverter.toLimitedSliceDto(reviewSlice, reviewList);
    }

    //TODO: ReviewQuerySerivce에서 메소드 재활용하기.
    private ReviewResponseDTO.ReviewSlice getReviewSlice(Slice<Review> reviewSlice) {
        List<ReviewResponseDTO.Single> reviewList = reviewSlice.stream().map(review ->{
            List<PurchaseLinkResponseDTO> purchaseLinkList =
                    reviewPurchaseLinkRepository.findPurchaseLinksByReviewId(review.getId()).stream()
                            .map(PurchaseLinkResponseDTO::new).toList();

            return ReviewConverter.toSingleDTO(review, purchaseLinkList);
        }).toList();

        return ReviewConverter.toSliceDTO(reviewSlice, reviewList);
    }
}
