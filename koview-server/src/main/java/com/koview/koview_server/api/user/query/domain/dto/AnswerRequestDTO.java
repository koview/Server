package com.koview.koview_server.api.user.query.domain.dto;

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
@Schema(description = "QueryAnswer Request")
public class AnswerRequestDTO {

    @NotBlank
    @Schema(description = "답변 내용", example = "답변 내용 테스트")
    private String content;

    // @NotBlank
    @Schema(description = "리뷰 id", example = "1")
    private Long reviewId;
}
