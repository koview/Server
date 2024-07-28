package com.koview.koview_server.like.controller;

import com.koview.koview_server.global.apiPayload.ApiResult;
import com.koview.koview_server.like.domain.dto.LikeResponseDTO;
import com.koview.koview_server.like.service.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
@Tag(name = "Likes", description = "Likes API")
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/create")
    @Operation(description = "한 리뷰의 좋아요 등록")
    public ApiResult<LikeResponseDTO> createLikes(@RequestParam(name = "reviewId") Long reviewId) {
        return ApiResult.onSuccess(likesService.createLikes(reviewId));
    }

    @DeleteMapping("/delete")
    @Operation(description = "한 리뷰의 좋아요 취소")
    public ApiResult<LikeResponseDTO> cancelLikes(@RequestParam(name = "reviewId") Long reviewId) {
        return ApiResult.onSuccess(likesService.cancelLikes(reviewId));
    }
}
