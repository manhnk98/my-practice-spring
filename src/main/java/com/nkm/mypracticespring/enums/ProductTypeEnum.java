package com.nkm.mypracticespring.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ProductTypeEnum {

    Clothes("clothes"),
    Electronic("electronic"),
    Furniture("furniture");

    private final String name;

    ProductTypeEnum(String name) {
        this.name = name;
    }

    public static ProductTypeEnum get(String productType) {
        return Arrays.stream(ProductTypeEnum.values())
                .filter(value -> productType.equals(value.getName()))
                .findFirst()
                .orElse(null);
    }
}
