package com.koview.koview_server.like.service;

import com.koview.koview_server.like.domain.dto.LikeResponseDTO;

public interface LikesService {
    LikeResponseDTO createLikes(Long reviewId);
    LikeResponseDTO cancelLikes(Long reviewId);
}
