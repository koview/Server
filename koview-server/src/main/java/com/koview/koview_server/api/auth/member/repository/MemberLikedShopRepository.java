package com.koview.koview_server.api.auth.member.repository;

import com.koview.koview_server.api.auth.member.domain.MemberLikedShop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLikedShopRepository extends JpaRepository<MemberLikedShop, Long> {
}
