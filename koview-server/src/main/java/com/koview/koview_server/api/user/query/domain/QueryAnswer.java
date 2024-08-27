package com.koview.koview_server.api.user.query.domain;

import com.koview.koview_server.api.common.BaseTimeEntity;
import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.user.review.domain.Review;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueryAnswer extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "query_answer_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "query_id")
    private Query query;

    public void linkReview(Review review){
        if(this.review != null){
            this.review.getAnswerList().remove(this);
        }
        this.review=review;
        this.review.getAnswerList().add(this);
    }

    public void unLink(){
        if(this.review != null){
            this.review.getAnswerList().remove(this);
            this.review = null;
        }
    }
}
