package com.koview.koview_server.review.service;

import com.koview.koview_server.imageTest.domain.ImagePath;
import com.koview.koview_server.imageTest.repository.ImagePathRepository;
import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.repository.MemberRepository;
import com.koview.koview_server.review.domain.Review;
import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import com.koview.koview_server.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.review.domain.dto.ReviewResponseDTO;
import com.koview.koview_server.review.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ImagePathRepository imagePathRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("리뷰 생성")
    void createReview() {
        // Given
        ReviewRequestDTO requestDTO = new ReviewRequestDTO();
        Member member = new Member();
        Review review = new Review();
        List<ImagePath> imagePaths = List.of(new ImagePath(), new ImagePath());

        when(memberRepository.findByEmail(any())).thenReturn(Optional.of(member));
        when(imagePathRepository.findAllById(any())).thenReturn(imagePaths);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // When
        ReviewResponseDTO result = reviewService.createReview(requestDTO);

        // Then
        assertNotNull(result);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    @DisplayName("리뷰 삭제")
    void deleteReview() {
        // Given
        Long reviewId = 1L;
        doNothing().when(reviewRepository).deleteById(reviewId);

        // When
        reviewService.deleteReview(reviewId);

        // Then
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }

    @Test
    @DisplayName("리뷰 리스트 삭제")
    void deleteReviewList() {
        // Given
        ReviewRequestDTO.ReviewIdListDTO reviewIdListDTO = new ReviewRequestDTO.ReviewIdListDTO(List.of(1L, 2L));

        // When
        reviewService.deleteReviewList(reviewIdListDTO);

        // Then
        verify(reviewRepository, times(2)).deleteById(any(Long.class));
    }

    @Test
    @DisplayName("리뷰 전체 조회(이미지 2개 제한)")
    void findAllWithLimitedImages() {
        // Given
        List<Review> reviews = List.of(new Review(), new Review());
        for (Review review : reviews) {
            review.setMember(new Member());  // Set the member for each review
        }
        when(reviewRepository.findAll()).thenReturn(reviews);

        // When
        List<LimitedReviewResponseDTO> result = reviewService.findAllWithLimitedImages();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("리뷰 전체 조회")
    void findAll() {
        // Given
        List<Review> reviews = List.of(new Review(), new Review());
        for (Review review : reviews) {
            review.setMember(new Member());  // Set the member for each review
        }
        when(reviewRepository.findAll()).thenReturn(reviews);

        // When
        List<ReviewResponseDTO> result = reviewService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
