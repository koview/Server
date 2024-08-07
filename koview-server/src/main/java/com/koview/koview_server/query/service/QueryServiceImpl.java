package com.koview.koview_server.query.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koview.koview_server.global.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.global.apiPayload.exception.GeneralException;
import com.koview.koview_server.global.apiPayload.exception.MemberException;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.repository.MemberRepository;
import com.koview.koview_server.query.domain.Query;
import com.koview.koview_server.query.domain.QueryImage;
import com.koview.koview_server.query.domain.dto.QueryConverter;
import com.koview.koview_server.query.domain.dto.QueryRequestDTO;
import com.koview.koview_server.query.domain.dto.QueryResponseDTO;
import com.koview.koview_server.query.repository.QueryImageRepository;
import com.koview.koview_server.query.repository.QueryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QueryServiceImpl implements QueryService {

	private final MemberRepository memberRepository;
	private final QueryRepository queryRepository;
	private final QueryImageRepository queryImageRepository;

	@Override
	public QueryResponseDTO.toQueryDTO createQuery(QueryRequestDTO requestDTO) {
		Member member = validateMember();

		Query query = requestDTO.toEntity();
		query.setMember(member);

		List<QueryImage> images = queryImageRepository.findAllById(requestDTO.getImagePathIdList()).stream()
			.map(image -> QueryImage.builder()
				.url(image.getUrl())
				.query(query)
				.build())
			.collect(Collectors.toList());

		queryImageRepository.saveAll(images);
		query.addQueryImages(images);
		queryRepository.save(query);

		return new QueryResponseDTO.toQueryDTO(query);
	}

	@Override
	public QueryResponseDTO.QuerySlice findAll(Pageable pageable) {
		Slice<Query> querySlice = queryRepository.findAll(pageable);

		return getQuerySlice(querySlice);
	}

	@Override
	public QueryResponseDTO.toQueryDTO findById(Long queryId) {
		Query query = queryRepository.findById(queryId).orElseThrow(() -> new GeneralException(ErrorStatus.QUERY_NOT_FOUND));

		return new QueryResponseDTO.toQueryDTO(query);
	}

	private Member validateMember() {
		return memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(
			() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
	}

	private QueryResponseDTO.QuerySlice getQuerySlice(Slice<Query> querySlice) {
		List<QueryResponseDTO.Single> queryList = querySlice.stream()
			.map(QueryConverter::toSingleDTO)
			.collect(Collectors.toList());

		return QueryConverter.toSliceDTO(querySlice, queryList);
	}
}