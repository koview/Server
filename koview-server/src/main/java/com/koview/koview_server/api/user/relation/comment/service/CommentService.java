package com.koview.koview_server.api.user.relation.comment.service;

import com.koview.koview_server.api.user.relation.comment.domain.dto.CommentRequestDTO;
import com.koview.koview_server.api.user.relation.comment.domain.dto.CommentResponseDTO;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentResponseDTO.toCommentDTO createComment(Long reviewId, CommentRequestDTO requestDTO);
    void deleteComment(Long reviewId, Long commentId);
    CommentResponseDTO.CommentPaging findAll(Long reviewId, Pageable pageable);
}
