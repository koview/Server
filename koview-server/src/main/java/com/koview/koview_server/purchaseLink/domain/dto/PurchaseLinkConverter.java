package com.koview.koview_server.purchaseLink.domain.dto;

import com.koview.koview_server.purchaseLink.domain.PurchaseLink;
import com.koview.koview_server.purchaseLink.domain.ReviewPurchaseLink;
import com.koview.koview_server.purchaseLink.domain.VerifiedType;
import com.koview.koview_server.review.domain.Review;

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
}
