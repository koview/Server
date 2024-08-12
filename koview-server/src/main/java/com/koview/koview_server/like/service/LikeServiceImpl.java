package com.koview.koview_server.like.service;

import com.koview.koview_server.global.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.global.apiPayload.exception.MemberException;
import com.koview.koview_server.global.apiPayload.exception.ReviewException;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.like.domain.Like;
import com.koview.koview_server.like.domain.dto.LikeResponseDTO;
import com.koview.koview_server.like.repository.LikeRepository;
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
public class LikeServiceImpl implements LikeService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final LikeRepository likesRepository;

    @Override
    public LikeResponseDTO createLikes(Long reviewId) {
        Member currentMember = validateMember();
        Review review = validateReview(reviewId);

        Like newLike = Like.builder()
                .review(review)
                .member(currentMember)
                .build();
        likesRepository.save(newLike);

        review.increaseTotalLikesCount();
        reviewRepository.save(review);

        currentMember.isCurrentMemberLikedTrue();
        memberRepository.save(currentMember);

        return new LikeResponseDTO(newLike);
    }

    @Override
    public LikeResponseDTO cancelLikes(Long reviewId) {
        Review review = validateReview(reviewId);
        Like like = likesRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewException(ErrorStatus.LIKES_NOT_FOUND));

        if (review.getTotalLikesCount() > 0) {
            review.decreaseTotalLikesCount();
            reviewRepository.save(review);
        }
        likesRepository.delete(like);

        Member currentMember = validateMember();
        currentMember.isCurrentMemberLikedFalse();
        memberRepository.save(currentMember);

        return new LikeResponseDTO(like);
    }

    private Member validateMember() {
        return memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    private Review validateReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ErrorStatus.REVIEW_NOT_FOUND));
    }
}
