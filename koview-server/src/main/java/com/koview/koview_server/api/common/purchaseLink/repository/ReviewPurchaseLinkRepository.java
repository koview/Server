package com.koview.koview_server.api.common.purchaseLink.repository;

import com.koview.koview_server.api.common.purchaseLink.domain.PurchaseLink;
import com.koview.koview_server.api.common.purchaseLink.domain.ReviewPurchaseLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewPurchaseLinkRepository extends JpaRepository<ReviewPurchaseLink, Long> {
    @Query("SELECT rpl.purchaseLink FROM ReviewPurchaseLink rpl WHERE rpl.review.id = :reviewId")
    List<PurchaseLink> findPurchaseLinksByReviewId(@Param("reviewId") Long reviewId);

    void deleteAllByReviewId(Long reviewId);
}
