package com.nkm.mypracticespring.dto.access;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupResponse {

    private ShopInfo shop;

    private String accessToken;

    private String refreshToken;

}
