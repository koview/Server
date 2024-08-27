package com.koview.koview_server.api.user.query.domain.dto;

import com.koview.koview_server.api.user.query.domain.QueryAnswer;
import com.koview.koview_server.api.image.domain.dto.ImageResponseDTO;
import com.koview.koview_server.api.user.review.domain.Review;
import org.springframework.data.domain.Page;

import java.util.List;

public class AnswerConverter {
    public static AnswerResponseDTO.Single toSingleDTO(QueryAnswer queryAnswer, Boolean isLiked){
        Review review = queryAnswer.getReview();
        List<ImageResponseDTO> imageList = review.getReviewImageList().
                stream().map(ImageResponseDTO::new).limit(2).toList();

        Long likeCount = review.getTotalLikesCount();

        return AnswerResponseDTO.Single.builder()
                .answerId(queryAnswer.getId())
                .imageList(imageList)
                .content(queryAnswer.getContent())
                .writer(queryAnswer.getMember().getNickname())
                .commentCount((long) review.getCommentList().size())
                .totalLikeCount(likeCount != null ? likeCount : 0)
                .isLiked(isLiked)
                .updatedAt(queryAnswer.getLastModifiedDate().toLocalDate())
                .createdAt(queryAnswer.getCreatedDate().toLocalDate())
                .build();
    }
    public static AnswerResponseDTO.AnswerPaging toPagingDTO(Page<QueryAnswer> queryAnswerPage,
                                                             List<AnswerResponseDTO.Single> answerList){

        return AnswerResponseDTO.AnswerPaging.builder()
                .answerList(answerList)
                .getNumber(queryAnswerPage.getNumber()+1)
                .hasPrevious(queryAnswerPage.hasPrevious())
                .hasNext(queryAnswerPage.hasNext())
                .getTotalPages(queryAnswerPage.getTotalPages())
                .getTotalElements(queryAnswerPage.getTotalElements())
                .isFirst(queryAnswerPage.isFirst())
                .isLast(queryAnswerPage.isLast())
                .build();
    }
}
