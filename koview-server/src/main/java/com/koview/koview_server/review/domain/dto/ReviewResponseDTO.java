package com.koview.koview_server.review.domain.dto;

import com.koview.koview_server.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewResponseDTO {

    private Long id;
    private String content;
    private String writer;

    public ReviewResponseDTO(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.writer = review.getMember().getNickname();
    }
}
