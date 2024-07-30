package com.koview.koview_server.imageTest.service;

import com.koview.koview_server.global.s3.AmazonS3Manager;
import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.imageTest.domain.ReviewImage;
import com.koview.koview_server.imageTest.repository.ReviewImageRepository;
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
    private ReviewImageRepository reviewImageRepository;

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

        ReviewImage reviewImage = ReviewImage.builder()
                .url(url)
                .build();

        when(reviewImageRepository.save(any(ReviewImage.class))).thenReturn(reviewImage);

        // When
        ImageResponseDTO result = imageService.createReview(file);

        // Then
        assertNotNull(result);
        assertEquals(url, result.getUrl());
        verify(reviewImageRepository, times(1)).save(any(ReviewImage.class));
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

        ReviewImage reviewImage1 = ReviewImage.builder().url(url1).build();
        ReviewImage reviewImage2 = ReviewImage.builder().url(url2).build();

        when(reviewImageRepository.save(any(ReviewImage.class))).thenReturn(reviewImage1, reviewImage2);

        // When
        List<ImageResponseDTO> results = imageService.createReviews(files);

        // Then
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(url1, results.get(0).getUrl());
        assertEquals(url2, results.get(1).getUrl());
        verify(reviewImageRepository, times(2)).save(any(ReviewImage.class));
    }

    @Test
    @DisplayName("리뷰 이미지 삭제")
    void deleteReview() {
        // Given
        Long imageId = 1L;
        String url = "https://s3.example.com/test-image.jpg";

        ReviewImage reviewImage = ReviewImage.builder()
                .id(imageId)
                .url(url)
                .build();

        when(reviewImageRepository.findById(imageId)).thenReturn(Optional.of(reviewImage));

        // When
        String result = imageService.deleteReview(imageId);

        // Then
        assertEquals("삭제하였습니다.", result);
        verify(s3Manager, times(1)).deleteFile(url);
        verify(reviewImageRepository, times(1)).delete(reviewImage);
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

        ReviewImage reviewImage1 = ReviewImage.builder().id(imageId1).url(url1).build();
        ReviewImage reviewImage2 = ReviewImage.builder().id(imageId2).url(url2).build();

        when(reviewImageRepository.findById(imageId1)).thenReturn(Optional.of(reviewImage1));
        when(reviewImageRepository.findById(imageId2)).thenReturn(Optional.of(reviewImage2));

        // When
        String result = imageService.deleteReviews(imageIds);

        // Then
        assertEquals("이미지 리스트 삭제하였습니다.", result);
        verify(s3Manager, times(1)).deleteFile(url1);
        verify(s3Manager, times(1)).deleteFile(url2);
        verify(reviewImageRepository, times(1)).delete(reviewImage1);
        verify(reviewImageRepository, times(1)).delete(reviewImage2);
    }
}
