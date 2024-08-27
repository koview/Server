package com.koview.koview_server.api.auth.shop.controller;

import com.koview.koview_server.api.auth.shop.domain.dto.ShopResponseDTO;
import com.koview.koview_server.api.common.apiPayload.ApiResult;
import com.koview.koview_server.api.auth.shop.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Shop", description = "Shop API")
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/shops")
    @Operation(description = "사용자 선호 쇼핑몰 데이터 받아오는 API")
    public ApiResult<List<ShopResponseDTO>> findAllShops() {
        return ApiResult.onSuccess(shopService.findAll());
    }
}
