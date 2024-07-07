package com.koview.koview_server.global.image.repository;

import com.koview.koview_server.imageTest.domain.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UuidRepository extends JpaRepository<Uuid,Long> {
}
