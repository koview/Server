package com.koview.koview_server.api.user.query.domain.dto;

import com.koview.koview_server.api.image.domain.dto.ImageResponseDTO;
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
        private Long totalLikeCount;
        private Boolean isLiked;
        private Long commentCount;
        private LocalDate createdAt;
        private LocalDate updatedAt;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class AnswerPaging {
        private List<Single> answerList;
        private Integer getNumber;
        private Boolean hasPrevious;
        private Boolean hasNext;
        private Integer getTotalPages;
        private Long getTotalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }
}
