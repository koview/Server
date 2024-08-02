package com.koview.koview_server.review.service;

import com.koview.koview_server.image.domain.ReviewImage;
import com.koview.koview_server.image.repository.ReviewImageRepository;
import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.repository.MemberRepository;
import com.koview.koview_server.review.domain.Review;
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
    private ReviewImageRepository reviewImageRepository;

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
        ReviewRequestDTO requestDTO = mock(ReviewRequestDTO.class);
        Member member = new Member();
        Review review = new Review();
        List<ReviewImage> images = List.of(new ReviewImage(), new ReviewImage());

        when(memberRepository.findByEmail(any())).thenReturn(Optional.of(member));
        when(requestDTO.toEntity()).thenReturn(review);
        when(reviewImageRepository.findAllById(any())).thenReturn(images);

        // When
        ReviewResponseDTO.toReviewDTO result = reviewService.createReview(requestDTO);

        // Then
        assertNotNull(result);
        assertEquals(member, review.getMember());  // 멤버가 제대로 설정되었는지 확인
        verify(memberRepository, times(1)).findByEmail(any());
        verify(reviewRepository, times(1)).save(any(Review.class));
        verify(reviewImageRepository, times(1)).findAllById(any());
    }

    @Test
    @DisplayName("리뷰 삭제")
    void deleteReview() {
        // Given
        Long reviewId = 1L;

        // When
        reviewService.deleteReview(reviewId);

        // Then
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }

    @Test
    @DisplayName("리뷰 리스트 삭제")
    void deleteReviewList() {
        // Given
        List<Long> reviewIds = List.of(1L, 2L);
        ReviewRequestDTO.ReviewIdListDTO reviewIdListDTO = new ReviewRequestDTO.ReviewIdListDTO(reviewIds);

        // When
        reviewService.deleteReviewList(reviewIdListDTO);

        // Then
        verify(reviewRepository, times(2)).deleteById(any(Long.class));
    }
}
