package com.koview.koview_server.api.auth.member.domain;

import com.koview.koview_server.api.common.BaseTimeEntity;
import com.koview.koview_server.api.auth.shop.domain.Shop;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLikedShop extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_liked_shop_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
}
