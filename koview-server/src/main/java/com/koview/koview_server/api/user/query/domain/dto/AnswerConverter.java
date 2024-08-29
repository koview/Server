package com.koview.koview_server.api.user.query.domain.dto;

import com.koview.koview_server.api.auth.member.domain.Member;
import com.koview.koview_server.api.common.ProfileResponseDTO;
import com.koview.koview_server.api.image.domain.ProfileImage;
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

        ProfileResponseDTO profileInfo = getProfileInfo(review.getMember());

        Long likeCount = review.getTotalLikesCount();

        return AnswerResponseDTO.Single.builder()
                .answerId(queryAnswer.getId())
                .profileInform(profileInfo)
                .imageList(imageList)
                .content(queryAnswer.getContent())
                .commentCount((long) review.getCommentList().size())
                .totalLikeCount(likeCount != null ? likeCount : 0)
                .isLiked(isLiked)
                .reviewId(review.getId())
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
    private static ProfileResponseDTO getProfileInfo(Member member){
        ProfileImage profileImage = member.getProfileImage();
        boolean isProfileImage = profileImage != null;

        return ProfileResponseDTO.builder()
                .imageId(isProfileImage ? profileImage.getId() : null)
                .imageUrl(isProfileImage ? profileImage.getUrl() : null)
                .memberId(member.getId())
                .memberNickname(member.getNickname())
                .build();
    }
}
