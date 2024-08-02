package com.koview.koview_server.image.repository;

import com.koview.koview_server.image.domain.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImage,Long> {
}
