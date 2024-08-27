package com.koview.koview_server.api.user.query.service;

import com.koview.koview_server.api.user.query.domain.dto.QueryRequestDTO;
import com.koview.koview_server.api.user.query.domain.dto.QueryResponseDTO;
import org.springframework.data.domain.Pageable;

public interface QueryService {
	QueryResponseDTO.toQueryDTO createQuery(QueryRequestDTO requestDTO);
	void deleteQuery(Long queryId);
	QueryResponseDTO.QuerySlice findAll(Pageable pageable);
	QueryResponseDTO.DetailDTO findById(Long queryId, Pageable pageable);
}
