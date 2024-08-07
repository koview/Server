package com.koview.koview_server.query.domain.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.query.domain.Query;

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
		private List<String> imageList;
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
	public static class toQueryDTO {
		private Long queryId;
		private String content;
		private String writer;
		private List<ImageResponseDTO> imageList;
		private String createdAt;
		private String updatedAt;

		public toQueryDTO(Query query) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			this.queryId = query.getId();
			this.content = query.getContent();
			this.writer = query.getMember().getNickname();
			this.imageList = query.getQueryImageList() != null ?
				query.getQueryImageList().stream()
					.distinct()
					.map(ImageResponseDTO::new)
					.collect(Collectors.toList()) : null;
			this.createdAt = query.getCreatedDate().format(formatter);
			this.updatedAt = query.getLastModifiedDate().format(formatter);
		}
	}
}