package com.koview.koview_server.imageTest.service;

import com.koview.koview_server.global.s3.AmazonS3Manager;
import com.koview.koview_server.imageTest.domain.Uuid;
import com.koview.koview_server.global.image.repository.UuidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl {
    private final AmazonS3Manager s3Manager;
    private final UuidRepository uuidRepository;
    public String createReview(MultipartFile request) {

        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());

        return s3Manager.uploadFile(s3Manager.genReviewsKeyName(savedUuid), request);
    }
}
