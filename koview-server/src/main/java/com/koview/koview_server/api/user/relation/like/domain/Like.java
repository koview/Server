package com.koview.koview_server.api.user.relation.like.domain;


import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.user.review.domain.Review;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "likes")
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public void linkMember(Member member) {
        if (this.member != null) {
            this.member.getLikeList().remove(this);
        }

        this.member = member;
        if (member != null) {
            member.getLikeList().add(this);
        }
    }

    public void linkReview(Review review) {
        if (this.review != null) {
            this.review.getLikeList().remove(this);
        }

        this.review = review;
        if (review != null) {
            review.getLikeList().add(this);
        }
    }

    public void unLink(){
        if (this.member != null) {
            this.member.getLikeList().remove(this);
            this.member = null;
        }
        if (this.review != null) {
            this.review.getLikeList().remove(this);
            this.review = null;
        }
    }
}
