package com.koview.koview_server.image.repository;

import com.koview.koview_server.image.domain.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UuidRepository extends JpaRepository<Uuid,Long> {
}
