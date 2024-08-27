package com.koview.koview_server.api.user.query.service;

import com.koview.koview_server.api.user.query.domain.dto.AnswerRequestDTO;
import com.koview.koview_server.api.user.query.domain.dto.AnswerResponseDTO;
import org.springframework.data.domain.Pageable;

public interface QueryAnswerService {
    AnswerResponseDTO.Single createAnswer(AnswerRequestDTO request, Long queryId);

    AnswerResponseDTO.AnswerPaging findAll(Long queryId, Pageable pageable);
}
