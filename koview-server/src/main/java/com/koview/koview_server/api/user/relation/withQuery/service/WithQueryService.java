package com.koview.koview_server.api.user.relation.withQuery.service;

import com.koview.koview_server.api.user.relation.withQuery.domain.dto.WithQueryResponseDTO;

public interface WithQueryService {
	WithQueryResponseDTO createWithQuery(Long queryId);
	WithQueryResponseDTO cancelWithQuery(Long queryId);
}
