package com.koview.koview_server.purchaseLink.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class PurchaseLinkRequestDTO {

    @NotBlank
    @Schema(description = "구매링크", example = "url")
    public String purchaseLink;
    @NotBlank
    @Schema(description = "상품명", example = "temu")
    public String shopName;
}
