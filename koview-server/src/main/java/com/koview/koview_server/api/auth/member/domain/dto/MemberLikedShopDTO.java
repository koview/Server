package com.koview.koview_server.api.auth.member.domain.dto;

import com.koview.koview_server.api.auth.member.domain.MemberLikedShop;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLikedShopDTO {

    private final Long shopId;
    private final String shopName;

    /* Entity -> DTO */
    public MemberLikedShopDTO(MemberLikedShop memberLikedShop) {
        this.shopId = memberLikedShop.getShop().getId();
        this.shopName = memberLikedShop.getShop().getShopName();
    }
}