package com.koview.koview_server.imageTest.controller;

import com.koview.koview_server.global.apiPayload.ApiResult;
import com.koview.koview_server.imageTest.domain.ImagePath;
import com.koview.koview_server.imageTest.domain.dto.ImageTestRequestDTO;
import com.koview.koview_server.imageTest.service.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final ImageServiceImpl reviewImageService;
    @PostMapping(value = "/review",consumes = "multipart/form-data")
    public ApiResult<ImagePath> postReviewImage(@RequestPart MultipartFile image){

        return ApiResult.onSuccess(reviewImageService.createReview(image));
    }
    @PostMapping(value = "/reviews",consumes = "multipart/form-data")
    public ApiResult<List<ImagePath>> postReviewImage(@RequestPart List<MultipartFile> images){

        return ApiResult.onSuccess(reviewImageService.createReviews(images));
    }
    @DeleteMapping(value="/reviews/{reviewId}")
    public ApiResult<String> deleteReviewImage(@PathVariable Long reviewId){

        return ApiResult.onSuccess(reviewImageService.deleteReview(reviewId));
    }
    @DeleteMapping(value="/reviews")
    public ApiResult<String> deleteReviewImages(@RequestBody ImageTestRequestDTO.ImageIdListDTO request){

       