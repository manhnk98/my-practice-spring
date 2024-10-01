package com.nkm.mypracticespring.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElectronicPayload {

    private String manufacturer;

    private String model;

    private String color;

}
