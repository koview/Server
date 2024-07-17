package com.koview.koview_server.shop.domain.dto;

import com.koview.koview_server.shop.domain.Shop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ShopResponseDTO {

    private Long id;
    private String shopName;

    public ShopResponseDTO(Shop shop) {
        this.id = shop.getId();
        this.shopName = shop.getShopName();
    }
}
