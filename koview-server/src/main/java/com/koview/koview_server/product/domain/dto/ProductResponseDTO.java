package com.koview.koview_server.product.domain.dto;

import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.global.common.purchaseLink.PurchaseLinkResponseDTO;
import com.koview.koview_server.product.domain.Category;
import com.koview.koview_server.product.domain.StatusType;
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
        private Category category;
        private LocalDate restrictedDate;
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


}
