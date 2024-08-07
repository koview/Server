package com.koview.koview_server.query.service;

import org.springframework.data.domain.Pageable;
import com.koview.koview_server.query.domain.dto.QueryRequestDTO;
import com.koview.koview_server.query.domain.dto.QueryResponseDTO;

public interface QueryService {
	QueryResponseDTO.toQueryDTO createQuery(QueryRequestDTO requestDTO);
	QueryResponseDTO.QuerySlice findAll(Pageable pageable);
	QueryResponseDTO.QuerySlice findAllDetail(org.springframework.data.domain.Pageable pageable, Long clickedReviewId);
}
