package com.koview.koview_server.shop.service;

import com.koview.koview_server.shop.domain.dto.ShopResponseDTO;

import java.util.List;

public interface ShopService {

    List<ShopResponseDTO> findAll();
}
