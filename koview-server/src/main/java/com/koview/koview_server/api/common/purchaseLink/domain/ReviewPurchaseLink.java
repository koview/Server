package com.koview.koview_server.api.common.purchaseLink.domain;

import com.koview.koview_server.api.user.review.domain.Review;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewPurchaseLink {
    @Id
    @Column(name = "review_purchase_link_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_link_id", nullable = true)
    private PurchaseLink purchaseLink;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;
    private Integer count;
}
