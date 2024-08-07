package com.koview.koview_server.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koview.koview_server.query.domain.QueryImage;

public interface QueryImageRepository extends JpaRepository<QueryImage, Long> {
}