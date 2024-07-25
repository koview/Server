package com.koview.koview_server.review.domain.dto;

import com.koview.koview_server.imageTest.domain.ImagePath;
import com.koview.koview_server.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class ReviewResponseDTO {
    private Long id;
    private String content;
    private String writer;
    private List<Long> imagePathIdList;

    public ReviewResponseDTO(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.writer = review.getMember().getNickname();
        this.imagePathIdList = review.getImagePathList() != null ?
                review.getImagePathList().stream()
                        .map(ImagePath::getId)
                        .collect(Collectors.toList()) : null;
    }
}
