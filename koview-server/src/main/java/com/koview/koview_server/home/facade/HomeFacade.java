package com.koview.koview_server.home.facade;

import com.koview.koview_server.home.dto.HomeResponseDTO;
import com.koview.koview_server.product.domain.dto.ProductResponseDTO;
import com.koview.koview_server.product.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeFacade {
    private final ProductQueryService productQueryService;

    public HomeResponseDTO getHomeV1(){
        List<ProductResponseDTO.NameWithImage> fourRestrictedProducts = productQueryService.getFourRestrictedProducts();
        List<ProductResponseDTO.NameWithImage> fourFamousProducts = productQueryService.getFourBestFamousProducts();

        return HomeResponseDTO.builder()
                .restrictedFourProducts(fourRestrictedProducts)
                .famousFourProducts(fourFamousProducts)
                //TODO: 질문 기능 개발시 추가
                .build();

    }
}
