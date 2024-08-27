package com.koview.koview_server.api.common.purchaseLink.domain.dto;

import com.koview.koview_server.api.common.purchaseLink.domain.PurchaseLink;
import com.koview.koview_server.api.common.purchaseLink.domain.VerifiedType;
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
        boolean isProduct = purchaseLink.getProduct() == null;

        this.purchaseLinkId = purchaseLink.getId();
        this.productId = isProduct ? null : purchaseLink.getProduct().getId();
        this.purchaseUrl = purchaseLink.getPurchaseLink();
        this.shopName = purchaseLink.getShopName();
        this.verifiedType=purchaseLink.getVerifiedType();
    }
}
