package com.koview.koview_server.review.repository;

import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository  extends JpaRepository<Review, Long> {
    @Query("SELECT COUNT(r) FROM Review r WHERE r.member = :member AND r.id <= :clickedReviewId")
    Integer findReviewPosition(@Param("clickedReviewId") Long clickedReviewId, @Param("member") Member member);

    Slice<Review> findAllByMember(Member member, Pageable pageable);

    @Query("SELECT r FROM Review r JOIN ReviewPurchaseLink rp ON r.id = rp.review.id WHERE rp.purchaseLink.product.id = :productId")
    Page<Review> findAllByProductPurchaseLink(@Param("productId") Long productId, Pageable pageable);

    @Query("SELECT COUNT(r) FROM Review r JOIN ReviewPurchaseLink rp ON r.id = rp.review.id WHERE rp.purchaseLink.product.id = :productId")
    Long countByProductPurchaseLink(@Param("productId") Long productId);

    Slice<Review> findByContentContaining(String keyword, Pageable pageable);
}