package com.koview.koview_server.api.image.controller;

import com.koview.koview_server.api.image.domain.dto.ImageRequestDTO;
import com.koview.koview_server.api.image.service.ImageServiceImpl;
import com.koview.koview_server.api.common.apiPayload.ApiResult;
import com.koview.koview_server.api.image.domain.dto.ImageResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
@Tag(name = "Image", description = "Image API")
public class ImageController {

    private final ImageServiceImpl imageService;

    @PostMapping(value = "/review", consumes = "multipart/form-data")
    public ApiResult<ImageResponseDTO> postReviewImage(@RequestPart MultipartFile image) {
        return ApiResult.onSuccess(imageService.createReview(image));
    }

    @PostMapping(value = "/reviews", consumes = "multipart/form-data")
    public ApiResult<List<ImageResponseDTO>> postReviewImage(@RequestPart List<MultipartFile> images) {
        return ApiResult.onSuccess(imageService.createReviews(images));
    }

    @DeleteMapping(value = "/reviews/{reviewId}")
    public ApiResult<String> deleteReviewImage(@PathVariable Long reviewId) {
        return ApiResult.onSuccess(imageService.deleteReview(reviewId));
    }

    @DeleteMapping(value = "/reviews")
    public ApiResult<String> deleteReviewImages(@RequestBody ImageRequestDTO.ImageIdListDTO request) {
        return ApiResult.onSuccess(imageService.deleteReviews(request.getImageIdList()));
    }

    @GetMapping
    @Operation(description = "이미지 데이터 받아오는 API")
    public ApiResult<List<ImageResponseDTO>> findAllImages() {
        return ApiResult.onSuccess(imageService.findAll());
    }

    @PostMapping(value = "/profile", consumes = "multipart/form-data")
    @Operation(description = "프로필 등록 API")
    public ApiResult<ImageResponseDTO> postProfileImage(@RequestPart MultipartFile image) {
        return ApiResult.onSuccess(imageService.createProfile(image));
    }

    @PostMapping(value = "/products/{productId}", consumes = "multipart/form-data")
    @Operation(description = "상품 이미지 API")
    public ApiResult<List<ImageResponseDTO>> postProductImage(@PathVariable("productId") Long productId,
                                                              @RequestPart("imageList") List<MultipartFile> imageList) {
        return ApiResult.onSuccess(imageService.createProducts(productId, imageList));
    }

    @PostMapping(value = "/query", consumes = "multipart/form-data")
    public ApiResult<ImageResponseDTO> postQueryImage(@RequestPart MultipartFile image) {
        return ApiResult.onSuccess(imageService.createQueryImage(image));
    }

    @PostMapping(value = "/queries", consumes = "multipart/form-data")
    public ApiResult<List<ImageResponseDTO>> postQueryImage(@RequestPart List<MultipartFile> images) {
        return ApiResult.onSuccess(imageService.createQueryImages(images));
    }

    @DeleteMapping(value = "/queries/{queryId}")
    public ApiResult<String> deleteQueryImage(@PathVariable Long queryId) {
        return ApiResult.onSuccess(imageService.deleteQueryImage(queryId));
    }

    @DeleteMapping(value = "/queries")
    public ApiResult<String> deleteQueryImages(@RequestBody ImageRequestDTO.ImageIdListDTO request) {
        return ApiResult.onSuccess(imageService.deleteQueryImages(request.getImageIdList()));
    }
}