package com.koview.koview_server.member.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.koview.koview_server.comment.domain.Comment;
import com.koview.koview_server.global.common.BaseTimeEntity;
import com.koview.koview_server.like.domain.Like;
import com.koview.koview_server.memberLikedShop.domain.MemberLikedShop;
import com.koview.koview_server.mypage.domain.ProfileImage;
import com.koview.koview_server.query.domain.Query;
import com.koview.koview_server.review.domain.Review;
import com.koview.koview_server.shop.domain.Shop;
import com.koview.koview_server.withQuery.domain.WithQuery;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    private Integer age; // 나이

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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_image_id")
    private ProfileImage profileImage;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Query> queryList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WithQuery> withQueryList = new ArrayList<>();

    /* 패스워드 암호화 관련 */
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.loginPw = passwordEncoder.encode(loginPw);
    }

    /* 권한 부여 */
    public void addMemberAuthority() {
        this.role = RoleType.USER;
    }

    public void linkMemberLikedShops(List<Shop> shops) {
        for (Shop shop : shops) {
            MemberLikedShop memberLikedShop = MemberLikedShop.builder()
                    .member(this)
                    .shop(shop)
                    .build();
            this.memberLikedShopList.add(memberLikedShop);
        }
    }

    public void addProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }
}