package com.koview.koview_server.api.auth.shop.service;

import com.koview.koview_server.api.auth.shop.domain.dto.ShopResponseDTO;
import com.koview.koview_server.api.auth.shop.repository.ShopRepository;
import com.koview.koview_server.api.auth.shop.domain.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    @Override
    public List<ShopResponseDTO> findAll() {
        List<Shop> all = shopRepository.findAll();
        List<ShopResponseDTO> responseDTOS = new ArrayList<>();
        for (Shop shop : all) {
            ShopResponseDTO responseDTO = new ShopResponseDTO(shop);
            responseDTOS.add(responseDTO);
        }
        return responseDTOS;
    }
}
