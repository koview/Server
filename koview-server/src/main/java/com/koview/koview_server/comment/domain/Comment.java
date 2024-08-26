package com.koview.koview_server.comment.domain;

import com.koview.koview_server.global.common.BaseTimeEntity;
import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.review.domain.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public void linkMember(Member member) {
        if(this.member != null) {
            this.member.getCommentList().remove(this);
        }

        this.member = member;
        if(member != null) {
            member.getCommentList().add(this);
        }
    }

    public void linkReview(Review review) {
        if(this.review != null) {
            this.review.getCommentList().remove(this);
        }

        this.review = review;
        if(review != null) {
            review.getCommentList().add(this);
        }
    }
}