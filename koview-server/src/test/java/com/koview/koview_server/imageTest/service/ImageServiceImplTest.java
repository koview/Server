package com.koview.koview_server.imageTest.service;

import com.koview.koview_server.global.s3.AmazonS3Manager;
import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.imageTest.domain.ImagePath;
import com.koview.koview_server.imageTest.repository.ImagePathRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {

    @Mock
    private AmazonS3Manager s3Manager;

    @Mock
    private ImagePathRepository imagePathRepository;

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
        MultipartFile file = mock(MultipartFile.class);
        String url = "https://s3.example.com/test-image.jpg";

        when(s3Manager.genReviewsKeyName(any())).thenReturn("reviews/test-key");
        when(s3Manager.uploadFile(any(), any())).thenReturn(url);

        ImagePath imagePath = ImagePath.builder()
                .url(url)
                .build();

        when(imagePathRepository.save(any(ImagePath.class))).thenReturn(imagePath);

        // When
        ImageResponseDTO result = imageService.createReview(file);

        // Then
        assertNotNull(result);
        assertEquals(url, result.getUrl());
        verify(imagePathRepository, times(1)).save(any(ImagePath.class));
    }

    @Test
    @DisplayName("리뷰 이미지들 생성")
    void createReviews() {
        // Given
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);
        List<MultipartFile> files = List.of(file1, file2);
        String url1 = "https://s3.example.com/test-image1.jpg";
        String url2 = "https://s3.example.com/test-image2.jpg";

        when(s3Manager.genReviewsKeyName(any())).thenReturn("reviews/test-key1", "reviews/test-key2");
        when(s3Manager.uploadFile(any(), any())).thenReturn(url1, url2);

        ImagePath imagePath1 = ImagePath.builder().url(url1).build();
        ImagePath imagePath2 = ImagePath.builder().url(url2).build();

        when(imagePathRepository.save(any(ImagePath.class))).thenReturn(imagePath1, imagePath2);

        // When
        List<ImageResponseDTO> results = imageService.createReviews(files);

        // Then
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(url1, results.get(0).getUrl());
        assertEquals(url2, results.get(1).getUrl());
        verify(imagePathRepository, times(2)).save(any(ImagePath.class));
    }

    @Test
    @DisplayName("리뷰 이미지 삭제")
    void deleteReview() {
        // Given
        Long imageId = 1L;
        String url = "https://s3.example.com/test-image.jpg";

        ImagePath imagePath = ImagePath.builder()
                .id(imageId)
                .url(url)
                .build();

        when(imagePathRepository.findById(imageId)).thenReturn(Optional.of(imagePath));

        // When
        String result = imageService.deleteReview(imageId);

        // Then
        assertEquals("삭제하였습니다.", result);
        verify(s3Manager, times(1)).deleteFile(url);
        verify(imagePathRepository, times(1)).delete(imagePath);
    }

    @Test
    @DisplayName("리뷰 이미지들 삭제")
    void deleteReviews() {
        // Given
        Long imageId1 = 1L;
        Long imageId2 = 2L;
        List<Long> imageIds = List.of(imageId1, imageId2);

        String url1 = "https://s3.example.com/test-image1.jpg";
        String url2 = "https://s3.example.com/test-image2.jpg";

        ImagePath imagePath1 = ImagePath.builder().id(imageId1).url(url1).build();
        ImagePath imagePath2 = ImagePath.builder().id(imageId2).url(url2).build();

        when(imagePathRepository.findById(imageId1)).thenReturn(Optional.of(imagePath1));
        when(imagePathRepository.findById(imageId2)).thenReturn(Optional.of(imagePath2));

        // When
        String result = imageService.deleteReviews(imageIds);

        // Then
        assertEquals("이미지 리스트 삭제하였습니다.", result);
        verify(s3Manager, times(1)).deleteFile(url1);
        verify(s3Manager, times(1)).deleteFile(url2);
        verify(imagePathRepository, times(1)).delete(imagePath1);
        verify(imagePathRepository, times(1)).delete(imagePath2);
    }
}
