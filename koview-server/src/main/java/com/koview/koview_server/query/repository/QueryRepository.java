package com.koview.koview_server.query.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryRepository extends JpaRepository<com.koview.koview_server.query.domain.Query, Long> {
}