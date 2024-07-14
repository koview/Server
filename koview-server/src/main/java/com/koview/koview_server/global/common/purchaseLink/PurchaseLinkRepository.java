package com.koview.koview_server.global.common.purchaseLink;

import com.koview.koview_server.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseLinkRepository extends JpaRepository<PurchaseLink, Long> {
    List<PurchaseLink> findAllByProduct(Product product);
}
