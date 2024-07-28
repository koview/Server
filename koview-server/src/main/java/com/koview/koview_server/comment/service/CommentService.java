package com.koview.koview_server.comment.service;

import com.koview.koview_server.comment.domain.dto.CommentRequestDTO;
import com.koview.koview_server.comment.domain.dto.CommentResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    CommentResponseDTO.toCommentDTO createComment(Long reviewId, CommentRequestDTO requestDTO);
    void deleteComment(Long reviewId, Long commentId);
    CommentResponseDTO.CommentPaging findAll(Long reviewId, Pageable pageable);
}
