package com.koview.koview_server.review.repository;

import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.review.domain.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository  extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.id = :clickedReviewId UNION ALL SELECT r FROM Review r WHERE r.id <> :clickedReviewId")
    Slice<Review> findAllWithClickedReviewFirst(@Param("clickedReviewId") Long clickedReviewId, Pageable pageable);

    Slice<Review> findAllByMember(Member member, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.member = :member ORDER BY CASE WHEN r.id = :clickedReviewId THEN 0 ELSE 1 END, r.id")
    Slice<Review> findAllByMemberWithClickedReviewFirst(@Param("member") Member member, @Param("clickedReviewId") Long clickedReviewId, Pageable pageable);
}