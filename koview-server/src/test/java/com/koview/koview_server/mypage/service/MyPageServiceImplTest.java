package com.koview.koview_server.mypage.service;

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

class MyPageServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private MyPageServiceImpl myPageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원의 리뷰 전체 조회(이미지 2개 제한)")
    void findAllByMemberWithLimitedImages() {
        // Given
        Member member = new Member();
        List<Review> reviews = List.of(new Review(), new Review());
        for (Review review : reviews) {
            review.setMember(member);  // Set the member for each review
        }

        when(memberRepository.findByEmail(any())).thenReturn(Optional.of(member));
        when(reviewRepository.findAllByMember(member)).thenReturn(reviews);

        // When
        List<LimitedReviewResponseDTO> result = myPageService.findAllByMemberWithLimitedImages();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("회원의 리뷰 전체 조회")
    void findAllByMember() {
        // Given
        Member member = new Member();
        List<Review> reviews = List.of(new Review(), new Review());
        for (Review review : reviews) {
            review.setMember(member);  // Set the member for each review
        }

        when(memberRepository.findByEmail(any())).thenReturn(Optional.of(member));
        when(reviewRepository.findAllByMember(member)).thenReturn(reviews);

        // When
        List<ReviewResponseDTO> result = myPageService.findAllByMember();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("회원의 리뷰 삭제")
    void deleteMyReview() {
        // Given
        Long reviewId = 1L;
        doNothing().when(reviewRepository).deleteById(reviewId);

        // When
        myPageService.deleteMyReview(reviewId);

        // Then
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }

    @Test
    @DisplayName("회원의 리뷰 리스트 삭제")
    void deleteMyReviewList() {
        // Given
        ReviewRequestDTO.ReviewIdListDTO reviewIdListDTO = new ReviewRequestDTO.ReviewIdListDTO(List.of(1L, 2L));

        // When
        myPageService.deleteMyReviewList(reviewIdListDTO);

        // Then
        verify(reviewRepository, times(2)).deleteById(any(Long.class));
    }
}
