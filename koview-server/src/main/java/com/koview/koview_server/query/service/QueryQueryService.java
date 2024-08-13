package com.koview.koview_server.query.service;

import com.koview.koview_server.query.domain.dto.QueryResponseDTO;

import java.util.List;

public interface QueryQueryService {
    List<QueryResponseDTO.Single> getFourQueries();
}
