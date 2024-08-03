package com.koview.koview_server.product.domain.dto;

import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.purchaseLink.domain.dto.PurchaseLinkResponseDTO;
import com.koview.koview_server.product.domain.Product;
import com.koview.koview_server.product.domain.ProductImage;
import com.koview.koview_server.review.domain.dto.LimitedReviewResponseDTO;
import org.springframework.data.domain.Slice;

import java.util.List;

public class ProductConverter {

    public static ProductResponseDTO.Single toSingleDTO(Product product, Long reviewsCount,
                                                        List<ImageResponseDTO> productImages,
                                                        List<PurchaseLinkResponseDTO> purchaseLinkList){
        return ProductResponseDTO.Single.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .createdDate(product.getCreatedDate().toLocalDate())
                .reviewCount(reviewsCount)
                .restrictedDate(product.getRestrictedDate())
                .status(product.getStatusType())
                .productImageUrls(productImages)
                .purchaseLinkList(purchaseLinkList)
                //.category(product.getCategory())TODO: 런칭 때는 이걸로 바꿔야함
                .category(product.getCategoryType())
                .build();
    }
    public static ProductResponseDTO.ProductSlice toSliceDTO(Slice<Product> productSlice,
                                                             List<ProductResponseDTO.Single> productList){
        return ProductResponseDTO.ProductSlice.builder()
                .productList(productList)
                .getNumber(productSlice.getNumber())
                .hasNext(productSlice.hasNext())
                .hasPrevious(productSlice.hasPrevious())
                .build();
    }

    public static ProductResponseDTO.Detail toDetailDTO(ProductResponseDTO.Single product,
                                                        LimitedReviewResponseDTO.ReviewPaging reviewPage){
        return ProductResponseDTO.Detail.builder()
                .detail(product)
                .reviewPaging(reviewPage)
                .build();
    }

    public static ImageResponseDTO toImageDTO(ProductImage productImage){
        return ImageResponseDTO.builder()
                .imageId(productImage.getId())
                .url(productImage.getUrl())
                .build();
    }
}
