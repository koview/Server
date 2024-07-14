package com.koview.koview_server.review.repository;

import com.koview.koview_server.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository  extends JpaRepository<Review, Long> {
}