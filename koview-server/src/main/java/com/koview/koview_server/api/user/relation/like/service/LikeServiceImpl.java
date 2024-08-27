package com.koview.koview_server.api.user.relation.like.service;

import com.koview.koview_server.api.user.relation.like.domain.dto.LikeResponseDTO;
import com.koview.koview_server.api.user.relation.like.repository.LikeRepository;
import com.koview.koview_server.api.common.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.api.common.apiPayload.exception.MemberException;
import com.koview.koview_server.api.common.apiPayload.exception.ReviewException;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.api.user.relation.like.domain.Like;
import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.auth.member.repository.MemberRepository;
import com.koview.koview_server.api.user.review.domain.Review;
import com.koview.koview_server.api.user.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        review.increaseTotalLikesCount();
        reviewRepository.save(review);

        newLike.linkMember(currentMember);
        newLike.linkReview(review);
        likesRepository.save(newLike);

        return new LikeResponseDTO(newLike);
    }

    @Override
    public LikeResponseDTO cancelLikes(Long reviewId) {
        Member currentMember = validateMember();

        Review review = validateReview(reviewId);
        Like like = likesRepository.findByReviewAndMember(review, currentMember)
                .orElseThrow(() -> new ReviewException(ErrorStatus.LIKES_NOT_FOUND));

        like.unLink();

        if (review.getTotalLikesCount() > 0) {
            review.decreaseTotalLikesCount();
            reviewRepository.save(review);
        }
        likesRepository.delete(like);

        currentMember.getLikeList().remove(like);
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
