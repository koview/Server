package com.koview.koview_server.api.auth.shop.repository;

import com.koview.koview_server.api.auth.shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findAllByIdIn(List<Long> shopId);
}
