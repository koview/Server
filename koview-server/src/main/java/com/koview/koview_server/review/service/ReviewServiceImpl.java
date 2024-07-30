package com.koview.koview_server.review.service;

import com.koview.koview_server.global.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.global.apiPayload.exception.MemberException;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.imageTest.domain.ImagePath;
import com.koview.koview_server.imageTest.repository.ImagePathRepository;
import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.repository.MemberRepository;
import com.koview.koview_server.purchaseLink.domain.PurchaseLink;
import com.koview.koview_server.purchaseLink.domain.dto.PurchaseLinkConverter;
import com.koview.koview_server.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import com.koview.koview_server.purchaseLink.repository.PurchaseLinkRepository;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final ImagePathRepository imagePathRepository;
    private final ReviewPurchaseLinkRepository reviewPurchaseLinkRepository;
    private final PurchaseLinkRepository purchaseLinkRepository;

    @Override
    public ReviewResponseDTO.toReviewDTO createReview(ReviewRequestDTO requestDTO) {
        Member member = validateMember();

        Review review = requestDTO.toEntity();
        review.setMember(member);

        List<ImagePath> images = imagePathRepository.findAllById(requestDTO.getImagePathIdList());
        for (ImagePath image : images) {
            image.addReview(review);
        }
        review.setImagePathList(images);

        reviewRepository.save(review);

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
                    .map(purchaseLink -> PurchaseLinkConverter.toReviewPurchaseLink(purchaseLink, review))
                    .forEach(reviewPurchaseLinkRepository::save);
        }

        return new ReviewResponseDTO.toReviewDTO(review);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public void deleteReviewList(ReviewRequestDTO.ReviewIdListDTO reviewIdListDTO) {
        for (Long reviewId : reviewIdListDTO.getReviewIdList()) {
            reviewRepository.deleteById(reviewId);
        }
    }

    @Override
    public LimitedReviewResponseDTO.ReviewSlice findAllWithLimitedImages(Pageable pageable) {
        Slice<Review> reviewSlice = reviewRepository.findAll(pageable);

        return getLimitedImageReviewSlice(reviewSlice);
    }

    @Override
    public ReviewResponseDTO.ReviewSlice findAll(Pageable pageable, Long clickedReviewId) {
        Slice<Review> reviewSlice = reviewRepository.findAllWithClickedReviewFirst(clickedReviewId, pageable);

        return getReviewSlice(reviewSlice);
    }

    private Member validateMember() {
        return memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(
                () -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    private LimitedReviewResponseDTO.ReviewSlice getLimitedImageReviewSlice(Slice<Review> reviewSlice) {
        List<LimitedReviewResponseDTO.Single> reviewList = reviewSlice.stream().map(ReviewConverter::toLimitedSingleDto).toList();

        return ReviewConverter.toLimitedSliceDto(reviewSlice, reviewList);
    }

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
