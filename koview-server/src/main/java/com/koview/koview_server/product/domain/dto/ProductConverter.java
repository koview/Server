package com.koview.koview_server.product.domain.dto;

import com.koview.koview_server.global.common.image.ImageResponseDTO;
import com.koview.koview_server.global.common.purchaseLink.PurchaseLinkResponseDTO;
import com.koview.koview_server.product.domain.Product;
import com.koview.koview_server.product.domain.ProductImage;
import org.springframework.data.domain.Slice;

import java.util.List;

public class ProductConverter {

    public static ProductResponseDTO.Single toSingleDTO(Product product,
                                                        List<ImageResponseDTO> productImages,
                                                        List<PurchaseLinkResponseDTO> purchaseLinkList){
        return ProductResponseDTO.Single.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .restrictedDate(product.getRestrictedDate())
                .status(product.getStatus())
                .productImageUrls(productImages)
                .purchaseLinkList(purchaseLinkList)
                .category(product.getCategory())
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

    public static ImageResponseDTO toImageDTO(ProductImage productImage){
        return ImageResponseDTO.builder()
                .imageId(productImage.getId())
                .url(productImage.getUrl())
                .build();
    }
}
