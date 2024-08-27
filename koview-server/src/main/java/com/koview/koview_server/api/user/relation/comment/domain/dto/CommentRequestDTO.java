package com.koview.koview_server.api.user.relation.comment.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Comment Create Request")
public class CommentRequestDTO {

    @NotBlank
    @Schema(description = "댓글 내용", example = "댓글 내용 테스트")
    private String content;
}
