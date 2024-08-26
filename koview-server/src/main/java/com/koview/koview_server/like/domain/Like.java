package com.koview.koview_server.like.domain;

import java.util.List;

import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.memberLikedShop.domain.MemberLikedShop;
import com.koview.koview_server.review.domain.Review;
import com.koview.koview_server.shop.domain.Shop;

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
}
