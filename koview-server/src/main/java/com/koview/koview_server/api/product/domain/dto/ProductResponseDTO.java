package com.koview.koview_server.api.product.domain.dto;

import com.koview.koview_server.api.image.domain.dto.ImageResponseDTO;
import com.koview.koview_server.api.common.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import com.koview.koview_server.api.product.domain.CategoryType;
import com.koview.koview_server.api.product.domain.StatusType;
import com.koview.koview_server.api.user.review.domain.dto.LimitedReviewResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


public class ProductResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Single{
        private Long productId;
        private String productName;
        // private Category category;
        private CategoryType category;
        private LocalDate createdDate;
        private LocalDate restrictedDate;
        private Long reviewCount;
        private StatusType status;
        private List<ImageResponseDTO> productImageUrls;
        private List<PurchaseLinkResponseDTO> purchaseLinkList;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProductSlice{
        private List<Single> productList;
        private int getNumber;
        private boolean hasPrevious;
        private boolean hasNext;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Detail{
        private Single detail;
        private LimitedReviewResponseDTO.ReviewPaging reviewPaging;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class NameWithImage{
        private String productName;
        private ImageResponseDTO image;
    }


}
