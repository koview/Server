package com.koview.koview_server.imageTest.repository;

import com.koview.koview_server.imageTest.domain.ImagePath;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagePathRepository extends JpaRepository<ImagePath,Long> {
}
