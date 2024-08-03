package com.koview.koview_server.product.domain;

import lombok.Getter;

@Getter
public enum CategoryType {
    CLOTHES("의류"), ACCESSORIES("패션 소품"), STATIONARY("학용품"), TOY("완구"), CHILDREN_ETC("기타"),
    SANITARY("학용품"), FOOD_CONTAINER("식품 용기"), GENERAL_ETC("기타");

    private final String description;
    CategoryType(String description) {
        this.description = description;
    }
}
