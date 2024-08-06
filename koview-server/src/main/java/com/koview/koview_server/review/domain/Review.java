package com.koview.koview_server.review.domain;

import java.util.ArrayList;
import java.util.List;

import com.koview.koview_server.comment.domain.Comment;
import com.koview.koview_server.global.common.BaseTimeEntity;
import com.koview.koview_server.image.domain.ReviewImage;
import com.koview.koview_server.like.domain.Like;
import com.koview.koview_server.member.domain.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> reviewImageList = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likeList = new ArrayList<>();

    private Long totalLikesCount = 0L;

    public void increaseTotalLikesCount() {
        if (this.totalLikesCount == null) {
            this.totalLikesCount = 0L;
        }
        this.totalLikesCount++;
    }

    public void decreaseTotalLikesCount() {
        if (this.totalLikesCount == null) {
            this.totalLikesCount = 0L;
        }
        this.totalLikesCount--;
    }

    public void addReviewImages(List<ReviewImage> reviewImages) {
        if(this.reviewImageList ==  null) {
            this.reviewImageList = new ArrayList<>();
        }
        for (ReviewImage image : reviewImages) {
            if (!this.reviewImageList.contains(image)) {
                image.addReview(this);
                this.reviewImageList.add(image);
            }
        }
    }
}
