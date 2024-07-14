package com.koview.koview_server.shop.repository;

import com.koview.koview_server.shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findAllByShopNameIn(List<String> shopName);
}
