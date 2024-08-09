package com.koview.koview_server.withQuery.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.koview.koview_server.global.apiPayload.ApiResult;
import com.koview.koview_server.like.domain.dto.LikeResponseDTO;
import com.koview.koview_server.withQuery.domain.dto.WithQueryResponseDTO;
import com.koview.koview_server.withQuery.service.WithQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/withquery")
@Tag(name = "WithQuery", description = "WithQuery API")
public class WithQueryController {

	private final WithQueryService withQueryService;

	@PostMapping("/create")
	@Operation(description = "질문에 나도 공감 등록")
	public ApiResult<WithQueryResponseDTO> createWithQuery(@RequestParam(name = "queryId") Long queryId) {
		return ApiResult.onSuccess(withQueryService.createWithQuery(queryId));
	}

	@DeleteMapping("/delete")
	@Operation(description = "질문에 나도 공감 취소")
	public ApiResult<WithQueryResponseDTO> cancelWithQuery(@RequestParam(name = "queryId") Long queryId) {
		return ApiResult.onSuccess(withQueryService.cancelWithQuery(queryId));
	}
}
