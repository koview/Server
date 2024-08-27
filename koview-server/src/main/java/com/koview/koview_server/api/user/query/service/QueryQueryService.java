package com.koview.koview_server.api.user.query.service;

import com.koview.koview_server.api.user.query.domain.dto.QueryResponseDTO;

import java.util.List;

public interface QueryQueryService {
    List<QueryResponseDTO.Single> getFourQueries();
}
