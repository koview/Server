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

    public void setReview(Review review){
        if(this.review != null){
            this.review.getAnswerList().remove(this);
        } // 답변 인스턴스를 만들었는데, 리뷰 정보가 있어.
        // 그러면, 해당 리뷰-답변리스트에서 이 답변을 일단 빼(초기화)
        this.review=review;
        // 상점 정보를 다시 세팅해주고
        this.review.getAnswerList().add(this);
        // 해당 상점에 다시 미션 추가해줌.
    }
}
