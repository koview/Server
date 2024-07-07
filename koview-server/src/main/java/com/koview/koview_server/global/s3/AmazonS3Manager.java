package com.koview.koview_server.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.koview.koview_server.config.AmazonConfig;
import com.koview.koview_server.image.domain.Uuid;
import com.koview.koview_server.image.repository.UuidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager{

    private final AmazonS3 amazonS3;
    private final AmazonConfig amazonConfig;

    public String uploadFile(String keyName, MultipartFile file){
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType()); // Content-Type 설정 추가
        try {
            amazonS3.putObject(new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(), metadata));
        }catch (IOException e){
            log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
        }

        return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
    }
    public String genReviewsKeyName(Uuid uuid) {
        return "reviews" + '/' + uuid.getUuid();
    }
    public String genProductsKeyName(Uuid uuid) {
        return "products" + '/' + uuid.getUuid();
    }
    public String genUserProfilesKeyName(Uuid uuid) {
        return "user-profiles" + '/' + uuid.getUuid();
    }
}