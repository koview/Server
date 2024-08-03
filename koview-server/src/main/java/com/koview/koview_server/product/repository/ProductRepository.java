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
    List<Product> findTop4ByStatusOrderByRestrictedDateDesc(StatusType status, Pageable pageable);

    @Query("SELECT p " +
            "FROM Product p " +
            "LEFT JOIN PurchaseLink pl ON p.id = pl.product.id " +
            "LEFT JOIN ReviewPurchaseLink rpl ON pl.id = rpl.purchaseLink.id " +
            "LEFT JOIN Review r ON rpl.review.id = r.id " +
            "WHERE p.status = :status " +
            "GROUP BY p.id, p.productName " +
            "ORDER BY COUNT(r.id) DESC")
    List<Product> findTop4ProductsByStatusAndReviewCount(@Param("status") StatusType status, Pageable pageable);

    Slice<Product> findAllByStatusOrderByRestrictedDateDesc(StatusType status, Pageable pageable);

    Slice<Product> findAllByStatusOrderByIdDesc(StatusType status, Pageable pageable);

    Slice<Product> findAllByCategoryAndStatusOrderByIdDesc(Category category, StatusType status, Pageable pageable);

    Slice<Product> findAllByCategoryAndStatusOrderByRestrictedDateDesc(Category category, StatusType status, Pageable pageable);

    Slice<Product> findAllBy(Pageable pageable);

    // categoryType
    Slice<Product> findAllByCategory(Category category,Pageable pageable);

    Slice<Product> findAllByCategoryType(CategoryType category, Pageable pageable);

    Slice<Product> findAllByCategoryTypeAndStatusOrderByIdDesc(CategoryType category, StatusType status, Pageable pageable);

    Slice<Product> findAllByCategoryTypeAndStatusOrderByRestrictedDateDesc(CategoryType category, StatusType status, Pageable pageable);


}
