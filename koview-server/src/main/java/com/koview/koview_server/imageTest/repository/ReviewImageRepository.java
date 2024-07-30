package com.koview.koview_server.imageTest.repository;

import com.koview.koview_server.imageTest.domain.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImage,Long> {
}
