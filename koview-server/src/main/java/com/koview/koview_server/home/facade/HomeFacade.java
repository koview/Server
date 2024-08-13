package com.koview.koview_server.home.facade;

import com.koview.koview_server.home.dto.HomeResponseDTO;
import com.koview.koview_server.product.domain.dto.ProductResponseDTO;
import com.koview.koview_server.product.service.ProductQueryService;
import com.koview.koview_server.query.domain.dto.QueryResponseDTO;
import com.koview.koview_server.query.service.QueryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeFacade {
    private final ProductQueryService productQueryService;
    private final QueryQueryService queryService;

    public HomeResponseDTO getHomeV1(){
        List<ProductResponseDTO.NameWithImage> fourRestrictedProducts = productQueryService.getFourRestrictedProducts();
        List<ProductResponseDTO.NameWithImage> fourFamousProducts = productQueryService.getFourBestFamousProducts();
        List<QueryResponseDTO.Single> fourQueries = queryService.getFourQueries();

        return HomeResponseDTO.builder()
                .restrictedFourProducts(fourRestrictedProducts)
                .famousFourProducts(fourFamousProducts)
                .lastFourQueries(fourQueries)
                //TODO: 질문 기능 개발시 추가
                .build();

    }
}
