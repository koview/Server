package com.koview.koview_server.query.service;

import org.springframework.data.domain.Pageable;

import com.koview.koview_server.query.domain.dto.QueryRequestDTO;
import com.koview.koview_server.query.domain.dto.QueryResponseDTO;

public interface QueryService {
	QueryResponseDTO.toQueryDTO createQuery(QueryRequestDTO requestDTO);
	void deleteQuery(Long queryId);
	QueryResponseDTO.QuerySlice findAll(Pageable pageable);
	QueryResponseDTO.DetailDTO findById(Long queryId, Pageable pageable);
}
