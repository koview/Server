package com.koview.koview_server.query.repository;

import com.koview.koview_server.query.domain.Query;
import com.koview.koview_server.query.domain.QueryAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryAnswerRepository extends JpaRepository<QueryAnswer, Long> {
    Page<QueryAnswer> findAllByQueryOrderById(Query query, Pageable pageable);
}
