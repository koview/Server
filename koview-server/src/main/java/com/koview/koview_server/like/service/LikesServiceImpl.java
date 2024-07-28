package com.koview.koview_server.like.service;

import com.koview.koview_server.global.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.global.apiPayload.exception.MemberException;
import com.koview.koview_server.global.apiPayload.exception.ReviewException;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.like.domain.Likes;
import com.koview.koview_server.like.domain.dto.LikeResponseDTO;
import com.koview.koview_server.like.repository.LikesRepository;
import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.repository.MemberRepository;
import com.koview.koview_server.review.domain.Review;
import com.koview.koview_server.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikesServiceImpl implements LikesService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final LikesRepository likesRepository;

    @Override
    public LikeResponseDTO createLikes(Long reviewId) {
        Member currentMember = validateMember();
        Review review = validateReview(reviewId);

        Likes newLike = Likes.builder()
                .review(review)
                .member(currentMember)
                .build();
        likesRepository.save(newLike);

        review.increaseTotalLikesCount(review.getTotalLikesCount());
        reviewRepository.save(review);

        return new LikeResponseDTO(newLike);
    }

    @Override
    public LikeResponseDTO cancelLikes(Long reviewId) {
        Review review = validateReview(reviewId);
        Likes likes = likesRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewException(ErrorStatus.LIKES_NOT_FOUND));

        if (review.getTotalLikesCount() > 0) {
            review.decreaseTotalLikesCount(review.getTotalLikesCount());
            reviewRepository.save(review);
        }
        likesRepository.delete(likes);
        return new LikeResponseDTO(likes);
    }

    private Member validateMember() {
        return memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    private Review validateReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ErrorStatus.REVIEW_NOT_FOUND));
    }
}
