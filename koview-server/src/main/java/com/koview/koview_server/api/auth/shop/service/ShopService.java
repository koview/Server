package com.koview.koview_server.api.auth.shop.service;

import com.koview.koview_server.api.auth.shop.domain.dto.ShopResponseDTO;

import java.util.List;

public interface ShopService {

    List<ShopResponseDTO> findAll();
}
