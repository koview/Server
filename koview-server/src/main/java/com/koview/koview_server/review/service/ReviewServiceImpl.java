package com.koview.koview_server.review.service;

import com.koview.koview_server.global.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.global.apiPayload.exception.MemberException;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.imageTest.domain.ImagePath;
import com.koview.koview_server.imageTest.repository.ImagePathRepository;
import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.repository.MemberRepository;
import com.koview.koview_server.review.domain.Review;
import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.review.domain.dto.ReviewResponseDTO;
import com.koview.koview_server.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final ImagePathRepository imagePathRepository;

    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO requestDTO) {
        Member member = validateMember();

        Review review = requestDTO.toEntity();
        review.setMember(member);

        List<ImagePath> images = imagePathRepository.findAllById(requestDTO.getImagePathIdList());
        for (ImagePath image : images) {
            image.addReview(review);
        }
        review.setImagePathList(images);

        reviewRepository.save(review);
        return new ReviewResponseDTO(review);
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
    public Slice<LimitedReviewResponseDTO> findAllWithLimitedImages(Pageable pageable) {
        Slice<Review> all = reviewRepository.findAll(pageable);
        List<LimitedReviewResponseDTO> responseDTOS = new ArrayList<>();
        for (Review review : all) {
            LimitedReviewResponseDTO responseDTO = new LimitedReviewResponseDTO(review);
            responseDTOS.add(responseDTO);
        }
        return new SliceImpl<>(responseDTOS, pageable, all.hasNext());
    }

    @Override
    public Slice<ReviewResponseDTO> findAll(Pageable pageable) {
        Slice<Review> all = reviewRepository.findAll(pageable);
        List<ReviewResponseDTO> responseDTOS = new ArrayList<>();
        for (Review review : all) {
            ReviewResponseDTO responseDTO = new ReviewResponseDTO(review);
            responseDTOS.add(responseDTO);
        }
        return new SliceImpl<>(responseDTOS, pageable, all.hasNext());
    }

    private Member validateMember() {
        return memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(
                () -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }
}
