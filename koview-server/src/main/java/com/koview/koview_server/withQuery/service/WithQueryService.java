package com.koview.koview_server.withQuery.service;

import com.koview.koview_server.withQuery.domain.dto.WithQueryResponseDTO;

public interface WithQueryService {
	WithQueryResponseDTO createWithQuery(Long queryId);
	WithQueryResponseDTO cancelWithQuery(Long queryId);
}
