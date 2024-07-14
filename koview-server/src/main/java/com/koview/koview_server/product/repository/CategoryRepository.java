package com.koview.koview_server.product.repository;

import com.koview.koview_server.product.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
