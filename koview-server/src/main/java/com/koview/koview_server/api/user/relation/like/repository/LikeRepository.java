package com.koview.koview_server.api.user.relation.like.repository;

import com.koview.koview_server.api.user.relation.like.domain.Like;
import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.user.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByReviewAndMember(Review review, Member member);
    Boolean existsByMemberAndReview(Member member, Review review);
}
