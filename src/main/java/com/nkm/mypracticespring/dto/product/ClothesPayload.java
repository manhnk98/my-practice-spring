package com.nkm.mypracticespring.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClothesPayload {

    private String brand;

    private String size;

    private String material;

}
