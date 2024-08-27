package com.koview.koview_server.api.image.repository;

import com.koview.koview_server.api.image.domain.QueryImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryImageRepository extends JpaRepository<QueryImage, Long> {
}