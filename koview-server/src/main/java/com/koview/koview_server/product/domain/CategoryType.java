package com.koview.koview_server.product.domain;

import lombok.Getter;

@Getter
public enum CategoryType {
    CHILD_CLOTHES("아동복"), CHILD_ACCESSORIES("아동 패션 소품"), CHILD_STATIONARY("학용품"), CHILD_TOY("완구"), CHILD_ETC("기타"),
    SANITARY("위생용품"), FOOD_CONTAINER("식품 용기"), GENERAL_ETC("기타");

    private final String description;
    CategoryType(String description) {
        this.description = description;
    }
}
