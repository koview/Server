package com.koview.koview_server.query.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.koview.koview_server.like.repository.LikeRepository;
import com.koview.koview_server.purchaseLink.domain.PurchaseLink;
import com.koview.koview_server.purchaseLink.domain.dto.PurchaseLinkConverter;
import com.koview.koview_server.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import com.koview.koview_server.purchaseLink.repository.PurchaseLinkRepository;
import com.koview.koview_server.purchaseLink.repository.QueryPurchaseLinkRepository;
import com.koview.koview_server.query.domain.QueryAnswer;
import com.koview.koview_server.query.domain.dto.*;
import com.koview.koview_server.query.repository.QueryAnswerRepository;
import com.koview.koview_server.withQuery.repository.WithQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
	private final QueryPurchaseLinkRepository queryPurchaseLinkRepository;
	private final PurchaseLinkRepository purchaseLinkRepository;
	private final WithQueryRepository withQueryRepository;
	private final QueryAnswerRepository queryAnswerRepository;
	private final LikeRepository likeRepository;

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
		Query saveQuery = queryRepository.save(query);

		if (requestDTO.getPurchaseLinkList() != null) {
			requestDTO.getPurchaseLinkList().stream()
					.map(linkDTO -> {
						// PurchaseLink에 같은 링크가 있는지 확인
						Optional<PurchaseLink> optionalPurchaseLink = purchaseLinkRepository.findByPurchaseLink(linkDTO.purchaseLink);
						// 없으면, 새로 저장
						return optionalPurchaseLink.orElseGet(() -> {
							PurchaseLink newPurchaseLink = PurchaseLinkConverter.toPurchaseLink(linkDTO);
							return purchaseLinkRepository.save(newPurchaseLink);
						});
					})
					.map(purchaseLink -> PurchaseLinkConverter.toQueryPurchaseLink(purchaseLink, saveQuery))
					.forEach(queryPurchaseLinkRepository::save);
		}

		return new QueryResponseDTO.toQueryDTO(query, false);
	}

	@Override
	public void deleteQuery(Long queryId) {
		queryRepository.deleteById(queryId);
	}

	@Override
	public QueryResponseDTO.QuerySlice findAll(Pageable pageable) {
		Slice<Query> querySlice = queryRepository.findAll(pageable);

		return getQuerySlice(querySlice);
	}

	@Override
	public QueryResponseDTO.DetailDTO findById(Long queryId, Pageable pageable) {
		Member member = validateMember();

		Query query = queryRepository.findById(queryId).orElseThrow(() -> new GeneralException(ErrorStatus.QUERY_NOT_FOUND));
		query.increaseTotalViewCount();
		queryRepository.save(query);

		List<PurchaseLinkResponseDTO> purchaseLinkList =
				queryPurchaseLinkRepository.findPurchaseLinksByQueryId(query.getId()).stream()
						.map(PurchaseLinkResponseDTO::new).toList();
		Boolean isWithQuery = withQueryRepository.existsByMemberAndQuery(member, query);

		QueryResponseDTO.Single queryDTO = QueryConverter.toSingleDTO(query, isWithQuery, purchaseLinkList);

		Page<QueryAnswer> answerPaging = queryAnswerRepository.findAllByQueryOrderById(query, pageable);
		List<AnswerResponseDTO.Single> answerList = answerPaging.stream().map(queryAnswer -> {
					Boolean isLiked = likeRepository.existsByMemberAndReview(member, queryAnswer.getReview());

					return AnswerConverter.toSingleDTO(queryAnswer, isLiked);
				}).toList();

		AnswerResponseDTO.AnswerPaging answerPagingDTO = AnswerConverter.toPagingDTO(answerPaging, answerList);

		return QueryConverter.toDetailDTO(queryDTO, answerPagingDTO);
	}

	private Member validateMember() {
		return memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(
			() -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
	}

	private QueryResponseDTO.QuerySlice getQuerySlice(Slice<Query> querySlice) {
		Member member = validateMember();

		List<QueryResponseDTO.Single> queryList = querySlice.stream()
			.map(query -> {
				List<PurchaseLinkResponseDTO> purchaseLinkList =
						queryPurchaseLinkRepository.findPurchaseLinksByQueryId(query.getId()).stream()
								.map(PurchaseLinkResponseDTO::new).toList();

				Boolean isWithQuery = withQueryRepository.existsByMemberAndQuery(member, query);
				return QueryConverter.toSingleDTO(query, isWithQuery, purchaseLinkList);
			})
			.collect(Collectors.toList());

		return QueryConverter.toSliceDTO(querySlice, queryList);
	}
}