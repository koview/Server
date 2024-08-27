package com.koview.koview_server.api.page.mypage.service;

import com.koview.koview_server.api.image.domain.dto.MyProfileResponseDTO;
import com.koview.koview_server.api.common.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.api.common.apiPayload.exception.MemberException;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.api.user.relation.like.repository.LikeRepository;
import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.auth.member.repository.MemberRepository;
import com.koview.koview_server.api.image.domain.ProfileImage;
import com.koview.koview_server.api.common.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import com.koview.koview_server.api.common.purchaseLink.repository.ReviewPurchaseLinkRepository;
import com.koview.koview_server.api.user.review.domain.Review;
import com.koview.koview_server.api.user.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.api.user.review.domain.dto.ReviewConverter;
import com.koview.koview_server.api.user.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.api.user.review.domain.dto.ReviewResponseDTO;
import com.koview.koview_server.api.user.review.repository.ReviewRepository;
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
    private final LikeRepository likeRepository;

    @Override
    public LimitedReviewResponseDTO.ReviewSlice findAllByMemberWithLimitedImages(Pageable pageable) {
        Member member = validateMember();
        Slice<Review> reviewSlice = reviewRepository.findAllByMember(member, pageable);

        return getLimitedImageReviewSlice(reviewSlice);
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
        Member member = validateMember();

        List<LimitedReviewResponseDTO.Single> reviewList = reviewSlice.stream().map(review -> {
            Boolean isLiked = likeRepository.existsByMemberAndReview(member, review);
            return ReviewConverter.toLimitedSingleDto(review, isLiked);
        }).toList();

        return ReviewConverter.toLimitedSliceDto(reviewSlice, reviewList);
    }

    //TODO: ReviewQuerySerivce에서 메소드 재활용하기.
    private ReviewResponseDTO.ReviewSlice getReviewSlice(Slice<Review> reviewSlice) {
        Member member = validateMember();
        List<ReviewResponseDTO.Single> reviewList = reviewSlice.stream().map(review ->{

            List<PurchaseLinkResponseDTO> purchaseLinkList =
                    reviewPurchaseLinkRepository.findPurchaseLinksByReviewId(review.getId()).stream()
                            .map(PurchaseLinkResponseDTO::new).toList();
            Boolean isLiked = likeRepository.existsByMemberAndReview(member, review);

            return ReviewConverter.toSingleDTO(review, isLiked ,purchaseLinkList);
        }).toList();

        return ReviewConverter.toSliceDTO(reviewSlice, reviewList);
    }
}
