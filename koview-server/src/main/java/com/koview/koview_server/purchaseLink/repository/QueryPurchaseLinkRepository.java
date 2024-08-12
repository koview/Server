package com.koview.koview_server.purchaseLink.repository;

import com.koview.koview_server.purchaseLink.domain.PurchaseLink;
import com.koview.koview_server.purchaseLink.domain.QueryPurchaseLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QueryPurchaseLinkRepository extends JpaRepository<QueryPurchaseLink, Long> {
    @Query("SELECT qpl.purchaseLink FROM QueryPurchaseLink qpl WHERE qpl.query.id = :queryId")
    List<PurchaseLink> findPurchaseLinksByQueryId(@Param("queryId") Long queryId);
}
