package com.koview.koview_server.api.user.relation.withQuery.service;

import com.koview.koview_server.api.user.relation.withQuery.domain.dto.WithQueryResponseDTO;
import com.koview.koview_server.api.user.relation.withQuery.repository.WithQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koview.koview_server.api.common.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.api.common.apiPayload.exception.GeneralException;
import com.koview.koview_server.api.common.apiPayload.exception.MemberException;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.auth.member.repository.MemberRepository;
import com.koview.koview_server.api.user.query.domain.Query;
import com.koview.koview_server.api.user.query.repository.QueryRepository;
import com.koview.koview_server.api.user.relation.withQuery.domain.WithQuery;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WithQueryServiceImpl implements WithQueryService {

	private final MemberRepository memberRepository;
	private final QueryRepository queryRepository;
	private final WithQueryRepository withQueryRepository;

	@Override
	public WithQueryResponseDTO createWithQuery(Long queryId) {
		Member currentMember = validateMember();
		Query query = validateQuery(queryId);

		WithQuery newWithQuery = WithQuery.builder()
			.query(query)
			.member(currentMember)
			.build();
		withQueryRepository.save(newWithQuery);

		query.increaseTotalWithQueriesCount();
		queryRepository.save(query);

		//TODO: withQuery 내부로 리팩토링해야함
		List<WithQuery> withQueryList = currentMember.getWithQueryList();
		withQueryList.remove(newWithQuery);
		withQueryList.add(newWithQuery);

		memberRepository.save(currentMember);

		return new WithQueryResponseDTO(newWithQuery);
	}

	@Override
	public WithQueryResponseDTO cancelWithQuery(Long queryId) {
		Member currentMember = validateMember();
		Query query = validateQuery(queryId);
		WithQuery withQuery = withQueryRepository.findByQueryAndMember(query, currentMember)
			.orElseThrow(() -> new GeneralException(ErrorStatus.WITH_QUERY_NOT_FOUND));

		if (query.getTotalWithQueriesCount() > 0) {
			query.decreaseTotalWithQueriesCount();
			queryRepository.save(query);
		}
		withQueryRepository.delete(withQuery);

		currentMember.getWithQueryList().remove(withQuery);
		memberRepository.save(currentMember);

		return new WithQueryResponseDTO(withQuery);
	}

	private Member validateMember() {
		return memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
	}

	private Query validateQuery(Long queryId) {
		return queryRepository.findById(queryId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.QUERY_NOT_FOUND));
	}
}
