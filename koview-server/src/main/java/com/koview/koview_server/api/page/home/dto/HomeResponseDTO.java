package com.koview.koview_server.api.page.home.dto;

import com.koview.koview_server.api.product.domain.dto.ProductResponseDTO;
import com.koview.koview_server.api.user.query.domain.dto.QueryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class HomeResponseDTO {
    private List<ProductResponseDTO.NameWithImage> famousFourProducts;
    private List<ProductResponseDTO.NameWithImage> restrictedFourProducts;
    private List<QueryResponseDTO.Single> lastFourQueries;
    //TODO: 질의 4개 리스트 추가
}
