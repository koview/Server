package com.koview.koview_server.review.service;

import com.koview.koview_server.global.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.global.apiPayload.exception.MemberException;
import com.koview.koview_server.global.apiPayload.exception.ReviewException;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.repository.MemberRepository;
import com.koview.koview_server.review.domain.Review;
import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.review.domain.dto.ReviewResponseDTO;
import com.koview.koview_server.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO requestDTO) {
        Member member = memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        Review review = requestDTO.toEntity();
        review.setMember(member);
        reviewRepository.save(review);

        return new ReviewResponseDTO(review);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<ReviewResponseDTO> findAll() {
        List<Review> all = reviewRepository.findAll();
        List<ReviewResponseDTO> responseDTOS = new ArrayList<>();
        for (Review review : all) {
            ReviewResponseDTO responseDTO = new ReviewResponseDTO(review);
            responseDTOS.add(responseDTO);
        }
        return responseDTOS;
    }

    @Override
    public ReviewResponseDTO findById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewException(ErrorStatus.REVIEW_NOT_FOUND));
        return new ReviewResponseDTO(review);
    }

    @Override
    public List<ReviewResponseDTO> findAllByMember() {
        Member member = memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(
                () -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));

        List<Review> all = reviewRepository.findAllByMember(member);
        List<ReviewResponseDTO> responseDTOS = new ArrayList<>();

        for (Review review : all) {
            ReviewResponseDTO responseDTO = new ReviewResponseDTO(review);
            responseDTOS.add(responseDTO);
        }
        return responseDTOS;
    }

    @Override
    public void deleteMyReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
