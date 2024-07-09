package com.koview.koview_server.imageTest.service;

import com.koview.koview_server.global.s3.AmazonS3Manager;
import com.koview.koview_server.imageTest.domain.Uuid;
import com.koview.koview_server.global.image.repository.UuidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {

    @Mock
    private AmazonS3Manager s3Manager;

    @Mock
    private UuidRepository uuidRepository;

    @InjectMocks
    private ImageServiceImpl imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("리뷰 이미지 생성")
    void createReview() {
        // Given
        MultipartFile mockFile = mock(MultipartFile.class);
        String mockUuid = UUID.randomUUID().toString();
        Uuid savedUuid = Uuid.builder().uuid(mockUuid).build();
        String s3KeyName = "some-s3-key-name";
        String s3Url = "s3-url";

        when(uuidRepository.save(any())).thenReturn(savedUuid);
        when(s3Manager.genReviewsKeyName(any(Uuid.class))).thenReturn(s3KeyName);
        when(s3Manager.uploadFile(anyString(), any(MultipartFile.class))).thenReturn(s3Url);

        // When
        String result = imageService.createReview(mockFile);

        // Then
        assertEquals(s3Url, result);
        verify(uuidRepository, times(1)).save(any(Uuid.class));
        verify(s3Manager, times(1)).genReviewsKeyName(any(Uuid.class));
        verify(s3Manager, times(1)).uploadFile(anyString(), any(MultipartFile.class));
    }
}
