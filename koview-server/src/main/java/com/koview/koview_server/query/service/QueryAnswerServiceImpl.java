package com.koview.koview_server.query.service;

import com.koview.koview_server.global.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.global.apiPayload.exception.GeneralException;
import com.koview.koview_server.global.apiPayload.exception.MemberException;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.like.repository.LikeRepository;
import com.koview.koview_server.member.domain.Member;
import com.koview.koview_server.member.repository.MemberRepository;
import com.koview.koview_server.query.domain.Query;
import com.koview.koview_server.query.domain.QueryAnswer;
import com.koview.koview_server.query.domain.dto.AnswerConverter;
import com.koview.koview_server.query.domain.dto.AnswerRequestDTO;
import com.koview.koview_server.query.domain.dto.AnswerResponseDTO;
import com.koview.koview_server.query.repository.QueryAnswerRepository;
import com.koview.koview_server.query.repository.QueryRepository;
import com.koview.koview_server.review.domain.Review;
import com.koview.koview_server.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryAnswerServiceImpl implements QueryAnswerService{

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final QueryRepository queryRepository;
    private final QueryAnswerRepository queryAnswerRepository;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public AnswerResponseDTO.Single createAnswer(AnswerRequestDTO request, Long queryId) {
        Member member = validateMember();
        Review review = getReviewById(request.getReviewId());
        Query query = getQueryById(queryId);

        QueryAnswer answer = QueryAnswer.builder()
                .member(member)
                .review(review)
                .query(query)
                .content(request.getContent())
                .build();

        answer.setReview(review);
        QueryAnswer saveAnswer = queryAnswerRepository.save(answer);

        return AnswerConverter.toSingleDTO(saveAnswer, false);
    }

    private Query getQueryById(Long queryId) {
        return queryRepository.findById(queryId).orElseThrow(
                ()->new GeneralException(ErrorStatus.QUERY_NOT_FOUND));
    }

    private Member validateMember() {
        return memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(
                () -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    private Review getReviewById(Long reviewId){
        return reviewRepository.findById(reviewId).orElseThrow(
                ()-> new GeneralException(ErrorStatus.REVIEW_NOT_FOUND));
    }

}
