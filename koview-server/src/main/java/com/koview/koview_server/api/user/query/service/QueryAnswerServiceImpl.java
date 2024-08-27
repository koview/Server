package com.koview.koview_server.api.user.query.service;

import com.koview.koview_server.api.user.query.domain.QueryAnswer;
import com.koview.koview_server.api.user.query.domain.dto.AnswerConverter;
import com.koview.koview_server.api.user.query.domain.dto.AnswerRequestDTO;
import com.koview.koview_server.api.user.query.domain.dto.AnswerResponseDTO;
import com.koview.koview_server.api.user.query.repository.QueryAnswerRepository;
import com.koview.koview_server.api.user.query.repository.QueryRepository;
import com.koview.koview_server.api.common.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.api.common.apiPayload.exception.GeneralException;
import com.koview.koview_server.api.common.apiPayload.exception.MemberException;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.api.user.relation.like.repository.LikeRepository;
import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.auth.member.repository.MemberRepository;
import com.koview.koview_server.api.user.query.domain.Query;
import com.koview.koview_server.api.user.review.domain.Review;
import com.koview.koview_server.api.user.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        answer.linkReview(review);
        QueryAnswer saveAnswer = queryAnswerRepository.save(answer);

        return AnswerConverter.toSingleDTO(saveAnswer, false);
    }

    @Override
    public AnswerResponseDTO.AnswerPaging findAll(Long queryId, Pageable pageable) {
        Member member = validateMember();
        Query query = getQueryById(queryId);
        Page<QueryAnswer> answerPaging = queryAnswerRepository.findAllByQueryOrderById(query, pageable);

        List<AnswerResponseDTO.Single> answerList = answerPaging.stream().map(queryAnswer -> {
            Boolean isLiked = likeRepository.existsByMemberAndReview(member, queryAnswer.getReview());
            return AnswerConverter.toSingleDTO(queryAnswer, isLiked);
        }).toList();

        return AnswerConverter.toPagingDTO(answerPaging, answerList);
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
