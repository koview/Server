package com.koview.koview_server.query.repository;

import com.koview.koview_server.query.domain.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueryRepository extends JpaRepository<Query, Long> {
    List<Query> findTop4ByOrderById();
}