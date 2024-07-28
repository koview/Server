package com.koview.koview_server.like.domain.dto;

import com.koview.koview_server.like.domain.Like;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LikeResponseDTO {
    private Long likeId;

    public LikeResponseDTO(Like like) {
        this.likeId = like.getId();
    }
}