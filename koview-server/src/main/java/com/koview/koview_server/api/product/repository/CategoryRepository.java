package com.koview.koview_server.api.product.repository;

import com.koview.koview_server.api.product.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
