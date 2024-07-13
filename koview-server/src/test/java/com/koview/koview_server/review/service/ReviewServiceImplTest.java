package com.koview.koview_server.review.service;

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

import java.util.ArrayList;
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
        ReviewRequestDTO requestDTO = ReviewRequestDTO.builder()
                .content("This is a review")
                .build();

        Member member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .build();

        Review review = Review.builder()
                .id(1L)
                .content(requestDTO.getContent())
                .member(member)
                .build();

        when(memberRepository.findByEmail(any())).thenReturn(Optional.of(member));
        when(reviewRepository.save(any())).thenReturn(review);

        // When
        ReviewResponseDTO responseDTO = reviewService.createReview(requestDTO);

        // Then
        assertNotNull(responseDTO);
        assertEquals(review.getContent(), responseDTO.getContent());
        verify(reviewRepository, times(1)).save(any(Review.class));
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
    @DisplayName("모든 리뷰 조회")
    void findAll() {
        // Given
        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder().id(1L).content("Review 1").build());
        reviews.add(Review.builder().id(2L).content("Review 2").build());

        when(reviewRepository.findAll()).thenReturn(reviews);

        // When
        List<ReviewResponseDTO> responseDTOS = reviewService.findAll();

        // Then
        assertNotNull(responseDTOS);
        assertEquals(2, responseDTOS.size());
        assertEquals(reviews.get(0).getContent(), responseDTOS.get(0).getContent());
        assertEquals(reviews.get(1).getContent(), responseDTOS.get(1).getContent());
        verify(reviewRepository, times(1)).findAll();
    }
}
