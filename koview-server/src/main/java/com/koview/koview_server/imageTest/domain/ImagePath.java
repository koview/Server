package com.koview.koview_server.imageTest.domain;

import com.koview.koview_server.global.common.BaseTimeEntity;
import com.koview.koview_server.review.domain.Review;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class ImagePath extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String url;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public void addReview(Review review) {
        this.review = review;
    }
}
