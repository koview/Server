package com.koview.koview_server.comment.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.koview.koview_server.comment.domain.dto.CommentRequestDTO;
import com.koview.koview_server.comment.domain.dto.CommentResponseDTO;
import com.koview.koview_server.comment.service.CommentService;
import com.koview.koview_server.global.apiPayload.ApiResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Comment", description = "Comment API")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/create")
    @Operation(description = "댓글 등록")
    public ApiResult<CommentResponseDTO.toCommentDTO> createComment(@RequestParam(name = "reviewId") Long reviewId, @RequestBody CommentRequestDTO requestDTO) {
        CommentResponseDTO.toCommentDTO responseDTO = commentService.createComment(reviewId, requestDTO);
        return ApiResult.onSuccess(responseDTO);
    }

    @DeleteMapping("/comments/{commentId}/delete")
    @Operation(description = "댓글 삭제")
    public ApiResult<?> deleteReview(@RequestParam(name = "reviewId") Long reviewId, @PathVariable Long commentId) {
        commentService.deleteComment(reviewId, commentId);
        return ApiResult.onSuccess();
    }

    @GetMapping("/comments")
    @Operation(description = "한 리뷰의 댓글 전체 조회")
    @ApiResponse(responseCode = "200", description = "댓글 전체 조회 성공",
            content = @Content(schema = @Schema(implementation = CommentResponseDTO.CommentPaging.class)))
    public ApiResult<CommentResponseDTO.CommentPaging> findAll(
            @RequestParam(name = "reviewId") Long reviewId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ApiResult.onSuccess(commentService.findAll(reviewId, (PageRequest.of(page-1, size))));
    }
}
