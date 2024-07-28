package com.koview.koview_server.like.domain.dto;

import com.koview.koview_server.like.domain.Likes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LikeResponseDTO {
    private Long likesId;

    public LikeResponseDTO(Likes likes) {
        this.likesId = likes.getId();
    }
}