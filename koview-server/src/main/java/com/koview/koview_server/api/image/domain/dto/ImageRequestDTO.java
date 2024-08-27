package com.koview.koview_server.api.image.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "Image Request")
public class ImageRequestDTO {
    @Builder
    @Getter
    public static class ImageIdDTO{
        private Long imageId;

        @JsonCreator
        public ImageIdDTO(@JsonProperty("imageId") Long imageId) {
            this.imageId = imageId;
        }
    }
    @Builder
    @Getter
    public static class ImageIdListDTO {
        private List<Long> imageIdList;

        @JsonCreator
        public ImageIdListDTO(@JsonProperty("imageIdList") List<Long> imageIdList) {
            this.imageIdList = imageIdList;
        }
    }
}
