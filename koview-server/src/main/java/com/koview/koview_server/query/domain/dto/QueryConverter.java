package com.koview.koview_server.query.domain.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import org.springframework.data.domain.Slice;

import com.koview.koview_server.query.domain.Query;

public class QueryConverter {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static QueryResponseDTO.Single toSingleDTO(Query query, List<PurchaseLinkResponseDTO> purchaseLinkList) {
		return QueryResponseDTO.Single.builder()
				.queryId(query.getId())
				.content(query.getContent())
				.writer(query.getMember().getNickname())
                .profileImage(query.getMember().getProfileImage() != null ?
                        new ImageResponseDTO(query.getMember().getProfileImage()) : null)
                .imageList(query.getQueryImageList() != null ?
                        query.getQueryImageList().stream()
                                .map(ImageResponseDTO::new)
                                .collect(Collectors.toList()) : null)
                .totalWithQueriesCount(query.getTotalWithQueriesCount() != null ? query.getTotalWithQueriesCount() : 0L)
                .isCurrentMemberWithQuery(query.getMember().getIsCurrentMemberWithQuery())
                .totalViewCount(query.getTotalViewCount() != null ? query.getTotalViewCount() : 0L)
                .purchaseLinkList(purchaseLinkList)
				.createdAt(query.getCreatedDate().format(formatter))
				.updatedAt(query.getLastModifiedDate().format(formatter))
				.build();
	}

	public static QueryResponseDTO.QuerySlice toSliceDTO(Slice<Query> querySlice, List<QueryResponseDTO.Single> queryList) {
		return QueryResponseDTO.QuerySlice.builder()
			.queryList(queryList)
			.getNumber(querySlice.getNumber())
			.hasNext(querySlice.hasNext())
			.hasPrevious(querySlice.hasPrevious())
			.build();
	}
}
