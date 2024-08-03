package com.koview.koview_server.product.repository;

import com.koview.koview_server.product.domain.Category;
import com.koview.koview_server.product.domain.CategoryType;
import com.koview.koview_server.product.domain.Product;
import com.koview.koview_server.product.domain.StatusType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p " +
            "FROM Product p " +
            "LEFT JOIN PurchaseLink pl ON p.id = pl.product.id " +
            "LEFT JOIN ReviewPurchaseLink rpl ON pl.id = rpl.purchaseLink.id " +
            "LEFT JOIN Review r ON rpl.review.id = r.id " +
            "WHERE p.statusType = :status " +
            "GROUP BY p.id, p.productName " +
            "ORDER BY COUNT(r.id) DESC")
    List<Product> findTop4ProductsByStatusAndReviewCount(@Param("status") StatusType status, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE (:category IS NULL OR p.category = :category) " +
            "AND (:status IS NULL OR p.statusType = :status) " +
            "ORDER BY CASE WHEN :status = 'RESTRICTED' THEN p.restrictedDate ELSE p.id END DESC")
    Slice<Product> findAllByCategoryAndStatusType(@Param("category") Category category,
                                                      @Param("status") StatusType status,
                                                      Pageable pageable);

    // categoryType
    List<Product> findTop4ByStatusTypeOrderByRestrictedDateDesc(StatusType status, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE (:category IS NULL OR p.categoryType = :category) " +
            "AND (:status IS NULL OR p.statusType = :status) " +
            "ORDER BY CASE WHEN :status = 'RESTRICTED' THEN p.restrictedDate ELSE p.id END DESC")
    Slice<Product> findAllByCategoryTypeAndStatusType(@Param("category") CategoryType category,
                                              @Param("status") StatusType status,
                                              Pageable pageable);


}
