package com.koview.koview_server.like.repository;

import com.koview.koview_server.like.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByReviewId(Long reviewId);
    Likes findByReviewIdAndMemberId(Long reviewId, Long memberId);
}
