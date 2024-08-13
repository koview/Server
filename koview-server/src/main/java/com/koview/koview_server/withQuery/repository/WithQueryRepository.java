package com.koview.koview_server.withQuery.repository;

import java.util.Optional;

import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.query.domain.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.koview.koview_server.withQuery.domain.WithQuery;

public interface WithQueryRepository extends JpaRepository<WithQuery, Long> {
	Optional<WithQuery> findByQueryId(Long queryId);
	Boolean existsByMemberAndQuery(Member member, Query query);
}
