package com.koview.koview_server.api.user.relation.like.service;

import com.koview.koview_server.api.user.relation.like.domain.dto.LikeResponseDTO;

public interface LikeService {
    LikeResponseDTO createLikes(Long reviewId);
    LikeResponseDTO cancelLikes(Long reviewId);
}
