package com.koview.koview_server.member.domain;

import com.koview.koview_server.comment.domain.Comment;
import com.koview.koview_server.global.common.BaseTimeEntity;
import com.koview.koview_server.like.domain.Like;
import com.koview.koview_server.memberLikedShop.domain.MemberLikedShop;
import com.koview.koview_server.review.domain.Review;
import com.koview.koview_server.shop.domain.Shop;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; // 아이디

    @Column(nullable = false)
    private String loginPw; // 비밀번호

    @Column(nullable = false, unique = true)
    private String nickname; // 닉네임

    private int age; // 나이

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RoleType role; // 권한

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberLikedShop> memberLikedShopList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likeList = new ArrayList<>();

    /* 패스워드 암호화 관련 */
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.loginPw = passwordEncoder.encode(loginPw);
    }

    /* 권한 부여 */
    public void addMemberAuthority() {
        this.role = RoleType.USER;
    }

    public void addMemberLikedShops(List<Shop> shops) {
        for (Shop shop : shops) {
            MemberLikedShop memberLikedShop = MemberLikedShop.builder()
                    .member(this)
                    .shop(shop)
                    .build();
            this.memberLikedShopList.add(memberLikedShop);
        }
    }
}