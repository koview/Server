package com.koview.koview_server.query.domain.dto;

import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.query.domain.QueryAnswer;
import com.koview.koview_server.review.domain.Review;

import java.util.List;

public class AnswerConverter {
    public static AnswerResponseDTO.Single toSingleDTO(QueryAnswer queryAnswer, Boolean isLike){
        Review review = queryAnswer.getReview();
        List<ImageResponseDTO> imageList = review.getReviewImageList().
                stream().map(ImageResponseDTO::new).limit(2).toList();

        return AnswerResponseDTO.Single.builder()
                .answerId(queryAnswer.getId())
                .imageList(imageList)
                .content(queryAnswer.getContent())
                .writer(queryAnswer.getMember().getNickname())
                .commentCount((long) review.getCommentList().size())
                .likeCount(review.getTotalLikesCount())
                .isLike(isLike)
                .updatedAt(queryAnswer.getLastModifiedDate().toLocalDate())
                .createdAt(queryAnswer.getCreatedDate().toLocalDate())
                .build();
    }
}
