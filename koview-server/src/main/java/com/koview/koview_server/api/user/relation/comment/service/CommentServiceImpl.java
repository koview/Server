package com.koview.koview_server.api.user.relation.comment.service;

import com.koview.koview_server.api.user.relation.comment.domain.dto.CommentConverter;
import com.koview.koview_server.api.user.relation.comment.domain.dto.CommentRequestDTO;
import com.koview.koview_server.api.user.relation.comment.domain.dto.CommentResponseDTO;
import com.koview.koview_server.api.user.relation.comment.repository.CommentRepository;
import com.koview.koview_server.api.user.relation.comment.domain.Comment;
import com.koview.koview_server.api.common.apiPayload.code.status.ErrorStatus;
import com.koview.koview_server.api.common.apiPayload.exception.MemberException;
import com.koview.koview_server.api.common.apiPayload.exception.ReviewException;
import com.koview.koview_server.global.security.util.SecurityUtil;
import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.auth.member.repository.MemberRepository;
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
@Transactional
public class CommentServiceImpl implements CommentService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentResponseDTO.toCommentDTO createComment(Long reviewId, CommentRequestDTO requestDTO) {
        Member member = validateMember();
        Review review = validateReview(reviewId);

        Comment comment = Comment.builder()
                .content(requestDTO.getContent())
                .build();
        comment.linkMember(member);
        comment.linkReview(review);
        commentRepository.save(comment);

        return new CommentResponseDTO.toCommentDTO(comment);
    }

    @Override
    public void deleteComment(Long reviewId, Long commentId) { //TODO: reviewId가 불필요해서 향후 삭제 하기
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ReviewException(ErrorStatus.COMMENT_NOT_FOUND));

        comment.unLink();

        commentRepository.delete(comment);
    }

    @Override
    public CommentResponseDTO.CommentPaging findAll(Long reviewId, Pageable pageable) {
        validateReview(reviewId);

        Page<Comment> commentPage = commentRepository.findByReviewId(reviewId, pageable);
        return getCommentPaging(commentPage);
    }

    private Member validateMember() {
        return memberRepository.findByEmail(SecurityUtil.getEmail()).orElseThrow(
                () -> new MemberException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    private Review validateReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ErrorStatus.REVIEW_NOT_FOUND));
    }

    private CommentResponseDTO.CommentPaging getCommentPaging(Page<Comment> commentPage) {
        List<CommentResponseDTO.Single> commentList = commentPage.stream().map(CommentConverter::toSingleDTO).toList();

        return CommentConverter.toSliceDTO(commentPage, commentList);
    }
}