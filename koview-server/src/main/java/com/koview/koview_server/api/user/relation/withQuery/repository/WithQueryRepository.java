package com.koview.koview_server.api.user.relation.withQuery.repository;

import java.util.Optional;

import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.user.query.domain.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.koview.koview_server.api.user.relation.withQuery.domain.WithQuery;

public interface WithQueryRepository extends JpaRepository<WithQuery, Long> {
	Optional<WithQuery> findByQueryAndMember(Query query, Member member);
	Boolean existsByMemberAndQuery(Member member, Query query);
}
