package com.koview.koview_server.comment.domain.dto;

import com.koview.koview_server.comment.domain.Comment;
import org.springframework.data.domain.Page;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class CommentConverter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static CommentResponseDTO.Single toSingleDTO(Comment comment){
        return CommentResponseDTO.Single.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .writer(comment.getMember().getNickname())
                .createdDate(comment.getCreatedDate().format(formatter))
                .build();
    }
    public static CommentResponseDTO.CommentPaging toSliceDTO(Page<Comment> commentPaging,
                                                              List<CommentResponseDTO.Single> commentList){
        return CommentResponseDTO.CommentPaging.builder()
                .commentList(commentList)
                .getNumber(commentPaging.getNumber())
                .hasPrevious(commentPaging.hasPrevious())
                .hasNext(commentPaging.hasNext())
                .getTotalPages(commentPaging.getTotalPages())
                .getTotalElements(commentPaging.getTotalElements())
                .isFirst(commentPaging.isFirst())
                .isLast(commentPaging.isLast())
                .build();
    }
}
