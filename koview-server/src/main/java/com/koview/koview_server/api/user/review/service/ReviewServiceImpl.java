package com.koview.koview_server.api.user.review.service;

import com.koview.koview_server.api.common.purchaseLink.domain.ReviewPurchaseLink;
import com.koview.koview_server.api.user.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.api.user.review.domain.dto.ReviewResponseDTO;
import com.koview.koview_server.api.user.review.repository.ReviewRepository;
import com.koview.koview_server.api.common.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.api.common.apiPayload.exception.GeneralException;
import com.koview.koview_server.api.common.apiPayload.exception.MemberException;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.api.image.domain.ReviewImage;
import com.koview.koview_server.api.image.repository.ReviewImageRepository;
import com.koview.koview_server.api.user.relation.like.repository.LikeRepository;
import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.auth.member.repository.MemberRepository;
import com.koview.koview_server.api.common.purchaseLink.domain.PurchaseLink;
import com.koview.koview_server.api.common.purchaseLink.domain.dto.PurchaseLinkConverter;
import com.koview.koview_server.api.common.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import com.koview.koview_server.api.common.purchaseLink.repository.PurchaseLinkRepository;
import com.koview.koview_server.api.common.purchaseLink.repository.ReviewPurchaseLinkRepository;
import com.koview.koview_server.api.user.review.domain.Review;
import com.koview.koview_server.api.user.review.domain.dto.ReviewConverter;
import com.koview.koview_server.api.user.review.domain.dto.ReviewRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewPurchaseLinkRepository reviewPurchaseLinkRepository;
    private final PurchaseLinkRepository purchaseLinkRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final LikeRepository likeRepository;

    @Override
    public ReviewResponseDTO.toReviewDTO createReview(ReviewRequestDTO requestDTO) {
        Member member = validateMember();

        Review review = requestDTO.toEntity();
        review.linkMember(member);

        List<ReviewImage> images = reviewImageRepository.findAllById(requestDTO.getImagePathIdList()).stream()
            .map(image -> ReviewImage.builder()
                .url(image.getUrl())
                .review(review)
                .build())
            .collect(Collectors.toList());

        reviewImageRepository.saveAll(images);
        review.linkReviewImages(images);
        Review saveReview = reviewRepository.save(review);


        if (requestDTO.getPurchaseLinkList() != null) {
            requestDTO.getPurchaseLinkList().stream()
                    .map(linkDTO -> {
                        // PurchaseLink에 같은 링크가 있는지 확인
                        Optional<PurchaseLink> optionalPurchaseLink = purchaseLinkRepository.findByPurchaseLink(linkDTO.purchaseLink);
                        // 없으면, 새로 저장
                        return optionalPurchaseLink.orElseGet(() -> {
                            PurchaseLink newPurchaseLink = PurchaseLinkConverter.toPurchaseLink(linkDTO);
                            return purchaseLinkRepository.save(newPurchaseLink);
                        });
                    })
                    .forEach(purchaseLink -> {
                        ReviewPurchaseLink reviewPurchaseLink = PurchaseLinkConverter.toReviewPurchaseLink(purchaseLink, saveReview);
                        reviewPurchaseLinkRepository.save(reviewPurchaseLink);
                    });
        }

        return new ReviewResponseDTO.toReviewDTO(review, false);
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new GeneralException(ErrorStatus.REVIEW_NOT_FOUND));
        reviewPurchaseLinkRepository.deleteAllByReviewId(reviewId);

        review.unLink(); // warning: 구매링크 삭제 위에 작성하면 hibernate 오류 발생
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public void deleteReviewList(ReviewRequestDTO.ReviewIdListDTO reviewIdListDTO) {
        for (Long reviewId : reviewIdListDTO.getReviewIdList()) deleteReview(reviewId);
    }

    @Override
    public ReviewResponseDTO.ReviewSlice findAll(Pageable pageable) {
        Slice<Review> reviewSlice = reviewRepository.findAll(pageable);

        return getReviewSlice(reviewSlice);
    }

    @Override
    public ReviewResponseDTO.ReviewSlice findAllByMember(Pageable pageable, Long clickedReviewId, Long memberId) {

        Slice<Review> reviewSlice;
        Member member;
        if (memberId == null) {
            member = validateMember();
        }
        else{
            member = memberRepository.findById(memberId).orElseThrow(()->
                    new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        }

        if(clickedReviewId != null){

            int reviewPosition = reviewRepository.findReviewPosition(clickedReviewId, member);
            int pageNumber = (reviewPosition - 1) / pageable.getPageSize();

            reviewSlice = reviewRepository.findAllByMember(member, PageRequest.of(pageNumber,pageable.getPageSize()));
        } else{
            reviewSlice = reviewRepository.findAllByMember(member, pageable);
        }

        return getReviewSlice(reviewSlice);
    }

    @Override
    public ReviewResponseDTO.ReviewSlice searchReviews(String keyword, Pageable pageable) {
        Slice<Review> reviewSlice = reviewRepository.findByContentContaining(keyword, pageable);
        return getReviewSlice(reviewSlice);
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

    private ReviewResponseDTO.ReviewSlice getReviewSlice(Slice<Review> reviewSlice) {
        Member member = validateMember();

        List<ReviewResponseDTO.Single> reviewList = reviewSlice.stream().map(review ->{
            List<PurchaseLinkResponseDTO> purchaseLinkList =
                    reviewPurchaseLinkRepository.findPurchaseLinksByReviewId(review.getId()).stream()
                            .map(PurchaseLinkResponseDTO::new).toList();

            Boolean isLiked = likeRepository.existsByMemberAndReview(member, review);

            return ReviewConverter.toSingleDTO(review, isLiked, purchaseLinkList);
        }).toList();

        return ReviewConverter.toSliceDTO(reviewSlice, reviewList);
    }
}
