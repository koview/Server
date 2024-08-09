package com.koview.koview_server.withQuery.domain.dto;

import com.koview.koview_server.withQuery.domain.WithQuery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WithQueryResponseDTO {
	private Long withQueryId;

	public WithQueryResponseDTO(WithQuery withQuery) {
		this.withQueryId = withQuery.getId();
	}
}
