package com.koview.koview_server.review.controller;

import com.koview.koview_server.global.apiPayload.ApiResult;
import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.review.domain.dto.ReviewResponseDTO;
import com.koview.koview_server.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Tag(name = "Review", description = "Review API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/create")
    @Operation(description = "리뷰 등록")
    public ApiResult<ReviewResponseDTO> createReview(ReviewRequestDTO requestDTO) {
        ReviewResponseDTO responseDTO = reviewService.createReview(requestDTO);
        return ApiResult.onSuccess(responseDTO);
    }
}
