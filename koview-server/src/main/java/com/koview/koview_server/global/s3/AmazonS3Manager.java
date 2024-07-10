package com.koview.koview_server.global.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.koview.koview_server.config.AmazonConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager{

    private final AmazonS3 amazonS3;
    private final AmazonConfig amazonConfig;

    public String uploadFile(String keyName, MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType()); // Content-Type 설정 추가
        try {
            amazonS3.putObject(new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(), metadata));
        } catch (IOException e) {
            log.error("IOException at AmazonS3Manager uploadFile : {}", e);
            throw new RuntimeException("Failed to upload file", e);
        } catch (AmazonServiceException e) {
            log.error("AmazonServiceException at AmazonS3Manager uploadFile : {}", e);
            throw new RuntimeException("Failed to upload file", e);
        } catch (SdkClientException e) {
            log.error("SdkClientException at AmazonS3Manager uploadFile : {}", e);
            throw new RuntimeException("Failed to upload file", e);
        }

        return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
    }
    // 단건 삭제 메소드
    public void deleteFile(String imagePath) {
        try {
            String keyName = getObjectKeyFromUrl(imagePath);
            amazonS3.deleteObject(amazonConfig.getBucket(), keyName);
            log.info("Deleted file with key: {}", keyName);
        } catch (AmazonServiceException e) {
            log.error("AmazonServiceException at AmazonS3Manager deleteFile : {}", e);
            throw new RuntimeException("Failed to delete file", e);
        } catch (SdkClientException e) {
            log.error("SdkClientException at AmazonS3Manager deleteFile : {}", e);
            throw new RuntimeException("Failed to delete file", e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getObjectKeyFromUrl(String url) throws MalformedURLException {
        URL s3Url = new URL(url);
        return s3Url.getPath().substring(1); // /<object-key> -> <object-key>
    }
    public String genRandomUuid() {
        String uuid = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis();

        return uuid + "-" + timestamp;
    }
    public String genReviewsKeyName(String uuid) {
        return "reviews" + '/' + uuid;
    }
    public String genProductsKeyName(String uuid) {
        return "products" + '/' + uuid;
    }
    public String genUserProfilesKeyName(String uuid) {
        return "user-profiles" + '/' + uuid;
    }
}