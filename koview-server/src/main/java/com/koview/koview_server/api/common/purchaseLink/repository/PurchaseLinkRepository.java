package com.koview.koview_server.api.common.purchaseLink.repository;

import com.koview.koview_server.api.product.domain.Product;
import com.koview.koview_server.api.common.purchaseLink.domain.PurchaseLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseLinkRepository extends JpaRepository<PurchaseLink, Long> {
    List<PurchaseLink> findAllByProduct(Product product);
    Optional<PurchaseLink> findByPurchaseLink(String purchaseLink);

}
