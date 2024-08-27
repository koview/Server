package com.koview.koview_server.api.product.domain;

import com.koview.koview_server.api.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private CategoryType categoryType;

    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusType statusType ; // 일반, 인기, 규제

    private LocalDate restrictedDate;
}
