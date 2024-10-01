package com.nkm.mypracticespring.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FurniturePayload {

    private String brand;

    private String size;

    private String material;

}
