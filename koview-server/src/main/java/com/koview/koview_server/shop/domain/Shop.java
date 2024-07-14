package com.koview.koview_server.shop.domain;

import com.koview.koview_server.global.common.BaseTimeEntity;
import com.koview.koview_server.memberLikedShop.domain.MemberLikedShop;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @Column(nullable = false)
    private String shopName;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberLikedShop> memberLikedShopList = new ArrayList<>();
}
