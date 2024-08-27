package com.koview.koview_server.api.user.query.repository;

import com.koview.koview_server.api.user.query.domain.QueryAnswer;
import com.koview.koview_server.api.user.query.domain.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryAnswerRepository extends JpaRepository<QueryAnswer, Long> {
    Page<QueryAnswer> findAllByQueryOrderById(Query query, Pageable pageable);
    Long countByQuery(Query query);
}
