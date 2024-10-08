package com.koview.koview_server.api.user.review.domain;

import java.util.ArrayList;
import java.util.List;

import com.koview.koview_server.api.user.relation.comment.domain.Comment;
import com.koview.koview_server.api.common.BaseTimeEntity;
import com.koview.koview_server.api.image.domain.ReviewImage;
import com.koview.koview_server.api.user.relation.like.domain.Like;
import com.koview.koview_server.api.auth.member.domain.Member;

import com.koview.koview_server.api.user.query.domain.QueryAnswer;
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

import static lombok.Builder.*;

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

    @Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> reviewImageList = new ArrayList<>();

    @Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likeList = new ArrayList<>();

    @Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QueryAnswer> answerList = new ArrayList<>();

    @Default
    private Long totalLikesCount = 0L;

    public void linkMember(Member member) {
        // 기존 Member 와의 연관관계 제거
        if(this.member != null) {
            this.member.getReviewList().remove(this);
        }

        // 새로운 연관관계 설정
        this.member = member;
        if(member != null) {
            member.getReviewList().add(this);
        }
    }

    public void unLink() {
        // 모든 연관관계 제거
        if(this.member != null) {
            this.member.getReviewList().remove(this);
            this.member = null;
        }
    }

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

    public void linkReviewImages(List<ReviewImage> reviewImages) {
        if(this.reviewImageList ==  null) {
            this.reviewImageList = new ArrayList<>();
        }
        for (ReviewImage image : reviewImages) {
            if (!this.reviewImageList.contains(image)) {
                image.linkReview(this);
                this.reviewImageList.add(image);
            }
        }
    }
}
