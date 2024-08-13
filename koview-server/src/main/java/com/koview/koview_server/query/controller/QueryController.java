package com.koview.koview_server.query.controller;

import com.koview.koview_server.query.domain.dto.AnswerRequestDTO;
import com.koview.koview_server.query.domain.dto.AnswerResponseDTO;
import com.koview.koview_server.query.service.QueryAnswerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.koview.koview_server.global.apiPayload.ApiResult;
import com.koview.koview_server.query.domain.dto.QueryRequestDTO;
import com.koview.koview_server.query.domain.dto.QueryResponseDTO;
import com.koview.koview_server.query.service.QueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Query", description = "Query API")
public class QueryController {

	private final QueryService queryService;
	private final QueryAnswerService queryAnswerService;

	@PostMapping("/query/create")
	@Operation(description = "질문 등록")
	public ApiResult<QueryResponseDTO.toQueryDTO> createQuery(@RequestBody QueryRequestDTO requestDTO) {
		QueryResponseDTO.toQueryDTO responseDTO = queryService.createQuery(requestDTO);
		return ApiResult.onSuccess(responseDTO);
	}

	@DeleteMapping("/queries/{queryId}/delete")
	@Operation(description = "질문 삭제")
	public ApiResult<?> deleteQuery(@PathVariable Long queryId) {
		queryService.deleteQuery(queryId);
		return ApiResult.onSuccess();
	}

	@GetMapping("/queries")
	@Operation(description = "질문 전체 조회")
	public ApiResult<QueryResponseDTO.QuerySlice> getAllQueries(
		@Parameter(description = "페이지 번호(1부터 시작)")
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "20") int size) {
		return ApiResult.onSuccess(queryService.findAll(PageRequest.of(page - 1, size)));
	}

	@GetMapping("/queries/{queryId}")
	@Operation(description = "질문 상세 조회")
	public ApiResult<QueryResponseDTO.toQueryDTO> getQuery(@PathVariable(name = "queryId") Long queryId) {
		return ApiResult.onSuccess(queryService.findById(queryId));
	}

	@PostMapping("/queries/{queryId}/answer")
	@Operation(description = "답변 등록")
	public ApiResult<AnswerResponseDTO.Single> postAnswer(@PathVariable(name = "queryId") Long queryId,
														  @RequestBody AnswerRequestDTO requestDTO) {
		return ApiResult.onSuccess(queryAnswerService.createAnswer(requestDTO, queryId));
	}
	@GetMapping("/queries/{queryId}/answers")
	@Operation(description = "답변 리스트 조회")
	public ApiResult<AnswerResponseDTO.AnswerPaging> getAnswers(
			@PathVariable(name = "queryId") Long queryId,
			@Parameter(description = "페이지 번호(1부터 시작)")
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int size) {
		return ApiResult.onSuccess(queryAnswerService.findAll(queryId, PageRequest.of(page-1, size)));
	}
}