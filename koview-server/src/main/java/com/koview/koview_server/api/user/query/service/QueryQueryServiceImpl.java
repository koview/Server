package com.koview.koview_server.api.user.query.service;


import com.koview.koview_server.api.user.query.domain.dto.QueryConverter;
import com.koview.koview_server.api.user.query.domain.dto.QueryResponseDTO;
import com.koview.koview_server.api.user.query.repository.QueryAnswerRepository;
import com.koview.koview_server.api.user.query.repository.QueryRepository;
import com.koview.koview_server.api.common.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.api.common.apiPayload.exception.MemberException;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.auth.member.repository.MemberRepository;
import com.koview.koview_server.api.user.relation.withQuery.repository.WithQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QueryQueryServiceImpl implements QueryQueryService{
    private  final MemberRepository memberRepository;
    private final QueryRepository queryRepository;
    private final WithQueryRepository withQueryRepository;
    private final QueryAnswerRepository queryAnswerRepository;
    @Override
    public List<QueryResponseDTO.Single> getFourQueries() {
        Member member = validateMember();

        return queryRepository.findTop4ByOrderById().stream().map(query -> {
            Long answerCount = queryAnswerRepository.countByQuery(query);
            Boolean isLiked = withQueryRepository.existsByMemberAndQuery(member, query);
            return QueryConverter.toSingleDTO(query, isLiked, answerCount, null);
        }).toList();
    }

    private Member validateMember() {
        return memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(
                () -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }
}
