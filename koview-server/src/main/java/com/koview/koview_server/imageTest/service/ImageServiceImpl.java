package com.koview.koview_server.imageTest.service;

import com.koview.koview_server.global.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.global.apiPayload.exception.GeneralException;
import com.koview.koview_server.global.s3.AmazonS3Manager;
import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.imageTest.domain.ImagePath;
import com.koview.koview_server.imageTest.repository.ImagePathRepository;
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
    private final ImagePathRepository imagePathRepository;

    @Transactional
    public ImageResponseDTO createReview(MultipartFile request) {
        String keyName = s3Manager.genReviewsKeyName(s3Manager.genRandomUuid());

        ImagePath savedImagePath = imagePathRepository.save(
                ImagePath.builder()
                        .url(s3Manager.uploadFile(keyName, request))
                        .build()
        );

        return new ImageResponseDTO(savedImagePath);
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
        ImagePath imagePath = imagePathRepository.findById(imageId).orElseThrow(() -> new GeneralException(ErrorStatus.IMAGE_NOT_FOUND));
        s3Manager.deleteFile(imagePath.getUrl());
        imagePathRepository.delete(imagePath);

        return "삭제하였습니다.";
    }

    @Transactional
    public String deleteReviews(List<Long> imageIds) {
        for (Long imageId : imageIds)
            deleteReview(imageId);
        return "이미지 리스트 삭제하였습니다.";
    }

    public List<ImageResponseDTO> findAll() {
        List<ImagePath> imagePaths = imagePathRepository.findAll();
        List<ImageResponseDTO> imageResponseDTOs = new ArrayList<>();
        for (ImagePath imagePath : imagePaths) {
            imageResponseDTOs.add(new ImageResponseDTO(imagePath));
        }
        return imageResponseDTOs;
    }
}