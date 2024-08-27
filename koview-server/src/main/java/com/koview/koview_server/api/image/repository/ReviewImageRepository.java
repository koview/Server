package com.koview.koview_server.api.image.repository;

import com.koview.koview_server.api.image.domain.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImage,Long> {
}
