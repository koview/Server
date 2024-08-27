package com.koview.koview_server.api.common.purchaseLink.domain.dto;

import com.koview.koview_server.api.common.purchaseLink.domain.PurchaseLink;
import com.koview.koview_server.api.common.purchaseLink.domain.QueryPurchaseLink;
import com.koview.koview_server.api.common.purchaseLink.domain.ReviewPurchaseLink;
import com.koview.koview_server.api.common.purchaseLink.domain.VerifiedType;
import com.koview.koview_server.api.user.query.domain.Query;
import com.koview.koview_server.api.user.review.domain.Review;

public class PurchaseLinkConverter {
    public static PurchaseLink toPurchaseLink(PurchaseLinkRequestDTO requestDTO){
        return PurchaseLink.builder()
                .purchaseLink(requestDTO.purchaseLink)
                .shopName(requestDTO.shopName)
                .verifiedType(VerifiedType.UNVERIFIED)
                //.product() null 저장
                .build();
    }

    public static ReviewPurchaseLink toReviewPurchaseLink(PurchaseLink purchaseLink, Review review){
        return ReviewPurchaseLink.builder()
                .purchaseLink(purchaseLink)
                .review(review)
                .count(0)
                .build();
    }

    public static QueryPurchaseLink toQueryPurchaseLink(PurchaseLink purchaseLink, Query query){
        return QueryPurchaseLink.builder()
                .purchaseLink(purchaseLink)
                .query(query)
                .build();
    }
}
