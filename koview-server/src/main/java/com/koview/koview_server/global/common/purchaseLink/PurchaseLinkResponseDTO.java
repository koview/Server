package com.koview.koview_server.global.common.purchaseLink;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PurchaseLinkResponseDTO {
    private Long purchaseLinkId;
    private Long productId;
    private String purchaseUrl;
    private String shopName;
    private VerifiedType verifiedType;

    /* Entity -> DTO */
    public PurchaseLinkResponseDTO(PurchaseLink purchaseLink) {
        this.purchaseLinkId = purchaseLink.getId();
        this.productId = purchaseLink.getProduct().getId();
        this.purchaseUrl = purchaseLink.getPurchaseLink();
        this.shopName = purchaseLink.getShopName();
        this.verifiedType=purchaseLink.getVerifiedType();
    }
}
