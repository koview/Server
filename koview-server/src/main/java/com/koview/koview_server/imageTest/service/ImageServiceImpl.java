package com.koview.koview_server.imageTest.service;

import com.koview.koview_server.global.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.global.apiPayload.exception.GeneralException;
import com.koview.koview_server.global.s3.AmazonS3Manager;
import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.imageTest.domain.ReviewImage;
import com.koview.koview_server.imageTest.repository.ReviewImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageServiceImpl {
    private final AmazonS3Manager s3Manager;
    private final ReviewImageRepository reviewImageRepository;

    @Transactional
    public ImageResponseDTO createReview(MultipartFile request) {
        String keyName = s3Manager.genReviewsKeyName(s3Manager.genRandomUuid());

        ReviewImage savedReviewImage = reviewImageRepository.save(
                ReviewImage.builder()
                        .url(s3Manager.uploadFile(keyName, request))
                        .build()
        );

        return new ImageResponseDTO(savedReviewImage);
    }

    @Transactional
    public List<ImageResponseDTO> createReviews(List<MultipartFile> requests) {
        List<ImageResponseDTO> urls = new ArrayList<>();

        for (MultipartFile request : requests)
            urls.add(createReview(request));

        return urls;
    }

    @Transactional
    public String deleteReview(Long imageId) {
        ReviewImage reviewImage = reviewImageRepository.findById(imageId).orElseThrow(() -> new GeneralException(ErrorStatus.IMAGE_NOT_FOUND));
        s3Manager.deleteFile(reviewImage.getUrl());
        reviewImageRepository.delete(reviewImage);

        return "삭제하였습니다.";
    }

    @Transactional
    public String deleteReviews(List<Long> imageIds) {
        for (Long imageId : imageIds)
            deleteReview(imageId);
        return "이미지 리스트 삭제하였습니다.";
    }

    public List<ImageResponseDTO> findAll() {
        List<ReviewImage> reviewImages = reviewImageRepository.findAll();
        List<ImageResponseDTO> imageResponseDTOs = new ArrayList<>();
        for (ReviewImage reviewImage : reviewImages) {
            imageResponseDTOs.add(new ImageResponseDTO(reviewImage));
        }
        return imageResponseDTOs;
    }
}