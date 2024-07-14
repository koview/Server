package com.koview.koview_server.global.common.purchaseLink;

import com.koview.koview_server.global.common.BaseTimeEntity;
import com.koview.koview_server.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseLink extends BaseTimeEntity {
    @Id
    @Column(name = "purchase_link_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @Lob@Column(nullable = false)
    private String purchaseLink;

    private String shopName;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private VerifiedType verifiedType = VerifiedType.UNDEFINED; // 판매 종료된, 미확인, 검증된 상태
}
