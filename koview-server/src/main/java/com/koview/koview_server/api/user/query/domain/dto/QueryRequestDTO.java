package com.koview.koview_server.api.user.query.domain.dto;

import java.util.List;

import com.koview.koview_server.api.common.purchaseLink.domain.dto.PurchaseLinkRequestDTO;
import com.koview.koview_server.api.user.query.domain.Query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Query Create Request")
public class QueryRequestDTO {

	@NotBlank
	@Schema(description = "질문 내용", example = "질문 내용 테스트")
	private String content;

	@Schema(description = "이미지 ID 리스트", example = "[1, 2, 3]")
	private List<Long> imagePathIdList;

	@NotBlank
	private List<PurchaseLinkRequestDTO> purchaseLinkList;

	public Query toEntity() {
		return Query.builder()
			.content(content)
			.build();
	}
}