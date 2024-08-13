package com.koview.koview_server.query.domain.dto;

import com.koview.koview_server.global.common.image.ImageResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class AnswerResponseDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class Single {
        private Long answerId;
        private String content;
        private String writer;
        private List<ImageResponseDTO> imageList;
        private Long likeCount;
        private Boolean isLike;
        private Long commentCount;
        private LocalDate createdAt;
        private LocalDate updatedAt;
    }
}
