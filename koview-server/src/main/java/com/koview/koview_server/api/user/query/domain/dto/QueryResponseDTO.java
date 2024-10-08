package com.koview.koview_server.api.user.query.domain.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.koview.koview_server.api.image.domain.dto.ImageResponseDTO;
import com.koview.koview_server.api.common.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import com.koview.koview_server.api.user.query.domain.Query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QueryResponseDTO {

	@Getter
	@Builder
	@AllArgsConstructor
	public static class Single {
		private Long queryId;
		private String content;
		private String writer;
		private ImageResponseDTO profileImage;
		private List<ImageResponseDTO> imageList;
		private Long totalWithQueryCount;
		private Boolean isWithQuery;
		private Long totalViewCount;
		private Long totalAnswerCount;
		private List<PurchaseLinkResponseDTO> purchaseLinkList;
		private String createdAt;
		private String updatedAt;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class QuerySlice {
		private List<QueryResponseDTO.Single> queryList;
		private Integer getNumber;
		private Boolean hasPrevious;
		private Boolean hasNext;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class DetailDTO {
		private Single detail;
		private AnswerResponseDTO.AnswerPaging answerPaging;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class toQueryDTO {
		private Long queryId;
		private String content;
		private String writer;
		private ImageResponseDTO profileImage;
		private List<ImageResponseDTO> imageList;
		private Long totalWithQueriesCount;
		private Boolean isWithQuery;
		private Long totalViewCount;
		private String createdAt;
		private String updatedAt;

		public toQueryDTO(Query query, Boolean isWithQuery) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			this.queryId = query.getId();
			this.content = query.getContent();
			this.writer = query.getMember().getNickname();
			this.profileImage = query.getMember().getProfileImage() != null ?
				new ImageResponseDTO(query.getMember().getProfileImage()) : null;
			this.imageList = query.getQueryImageList() != null ?
				query.getQueryImageList().stream()
					.distinct()
					.map(ImageResponseDTO::new)
					.collect(Collectors.toList()) : null;
			this.totalWithQueriesCount = query.getTotalWithQueriesCount() != null ? query.getTotalWithQueriesCount() : 0L;
			this.isWithQuery = isWithQuery;
			this.totalViewCount = query.getTotalViewCount() != null ? query.getTotalViewCount() : 0L;
			this.createdAt = query.getCreatedDate().format(formatter);
			this.updatedAt = query.getLastModifiedDate().format(formatter);
		}
	}
}