package com.koview.koview_server.mypage.service;

import com.koview.koview_server.api.auth.member.repository.MemberRepository;
import com.koview.koview_server.api.page.mypage.service.MyPageServiceImpl;
import com.koview.koview_server.api.user.review.domain.dto.ReviewRequestDTO;
import com.koview.koview_server.api.user.review.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    @DisplayName("회원의 리뷰 삭제")
    void deleteMyReview() {
        // Given
        Long reviewId = 1L;

        // When
        myPageService.deleteMyReview(reviewId);

        // Then
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }

    @Test
    @DisplayName("회원의 리뷰 리스트 삭제")
    void deleteMyReviewList() {
        // Given
        List<Long> reviewIds = List.of(1L, 2L);
        ReviewRequestDTO.ReviewIdListDTO reviewIdListDTO = new ReviewRequestDTO.ReviewIdListDTO(reviewIds);

        // When
        myPageService.deleteMyReviewList(reviewIdListDTO);

        // Then
        verify(reviewRepository, times(2)).deleteById(any(Long.class));
    }
}
