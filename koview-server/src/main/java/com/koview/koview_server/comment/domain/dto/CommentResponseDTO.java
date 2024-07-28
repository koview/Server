package com.koview.koview_server.comment.domain.dto;

import com.koview.koview_server.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponseDTO {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Single {

        private Long commentId;
        private String content;
        private String writer;
        private String createdDate;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CommentPaging {

        private List<CommentResponseDTO.Single> commentList;
        private int getNumber;
        private boolean hasPrevious;
        private boolean hasNext;
        private int getTotalPages;
        private long getTotalElements;
        private boolean isFirst;
        private boolean isLast;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class toCommentDTO {
        private Long commentId;
        private String content;
        private String writer;
        private String createdDate;

        public toCommentDTO(Comment comment) {
            this.commentId = comment.getId();
            this.content = comment.getContent();
            this.writer = comment.getMember().getNickname();
            this.createdDate = comment.getCreatedDate().format(formatter);
        }
    }
}
