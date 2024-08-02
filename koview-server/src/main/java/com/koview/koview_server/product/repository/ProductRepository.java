package com.koview.koview_server.product.repository;

import com.koview.koview_server.product.domain.Category;
import com.koview.koview_server.product.domain.Product;
import com.koview.koview_server.product.domain.StatusType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProductRepository extends JpaRepository<Product, Long> {
    Slice<Product> findAllByStatusOrderByRestrictedDateDesc(StatusType status, Pageable pageable);

    Slice<Product> findAllByStatusOrderByIdDesc(StatusType status, Pageable pageable);

    Slice<Product> findAllByCategoryAndStatusOrderByIdDesc(Category category, StatusType status, Pageable pageable);

    Slice<Product> findAllByCategoryAndStatusOrderByRestrictedDateDesc(Category category, StatusType status, Pageable pageable);

    Slice<Product> findAllBy(Pageable pageable);

    Slice<Product> findAllByCategory(Category category,Pageable pageable);
}
