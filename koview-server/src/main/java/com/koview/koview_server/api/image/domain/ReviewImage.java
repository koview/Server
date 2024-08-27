package com.koview.koview_server.api.image.domain;

import com.koview.koview_server.api.common.BaseTimeEntity;
import com.koview.koview_server.api.user.review.domain.Review;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class ReviewImage extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(unique = true)
    private String url;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public void linkReview(Review review) {
        this.review = review;

        if (review.getReviewImageList() != null && !review.getReviewImageList().contains(this)) {
            review.getReviewImageList().add(this);
        }
    }

    public void unLink(){
        if(this.review != null){
            this.review.getReviewImageList().remove(this);
            this.review = null;
        }
    }
}
