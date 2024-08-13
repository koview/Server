package com.koview.koview_server.query.service;

import com.koview.koview_server.query.domain.dto.AnswerRequestDTO;
import com.koview.koview_server.query.domain.dto.AnswerResponseDTO;

public interface QueryAnswerService {
    AnswerResponseDTO.Single createAnswer(AnswerRequestDTO request, Long queryId);
}
