package com.koview.koview_server.like.repository;

import com.koview.koview_server.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByReviewId(Long reviewId);
    Like findByReviewIdAndMemberId(Long reviewId, Long memberId);
}
