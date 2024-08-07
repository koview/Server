package com.koview.koview_server.query.repository;

import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QueryRepository extends JpaRepository<com.koview.koview_server.query.domain.Query, Long> {
	@Query("SELECT q FROM Query q WHERE q.id = :clickedQueryId UNION ALL SELECT q FROM Query q WHERE q.id <> :clickedQueryId")
	Slice<com.koview.koview_server.query.domain.Query> findAllWithClickedQueryFirst(@Param("clickedQueryId") Long clickedQueryId, org.springframework.data.domain.Pageable pageable);
}